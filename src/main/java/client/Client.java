package client;

import Connection.ConnectionObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import msg.CC.CCPingMsg;
import msg.CS.CSPingMsg;
import msg.CS.CSPortReportMsg;
import org.apache.commons.collections.map.HashedMap;
import server.ServerConfig;
import server.UserMap;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class Client {
    private Address serverAddress = new Address(ServerConfig.IP, ServerConfig.PORT);
    
    private ConnectionObject coToServer; // the ConnectionObject with a channel connect to the server
    
    private Address myAddress; // not contain the listen port
    
    private int listenPort; // listen port of the ServerBootstrap
    
    private String localhost = "127.0.0.1"; 
    
    private Map<Address, ConnectionObject> coMap = new HashedMap(); // address ---- other co
    
    private Map<Long, Address> addrMap = new HashedMap(); // other id ---- other address
    
    public void setLocalhost(String localhost){
        this.localhost = localhost;
    }
    public String getLocalhost(){
        return  this.localhost;
    }
    public void addOtherAddr(Long id, Address address){
        addrMap.put(id, address);
    }
    public Address getOtherAddr(Long id){
        if(addrMap.containsKey(id)){
            return addrMap.get(id);
        }else{
            return  null;
        }
    }
    public void addOtherCO(Address address, ConnectionObject CO){
        coMap.put(address,CO);
    }
    public ConnectionObject getOtherCO(Address address){
        if(coMap.containsKey(address)){
            return coMap.get(address);
        }else{
            return null;
        }
    }
    private Bootstrap b;
    private final long id ;
    public Client(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
    public Client(long id, int listenPort){
        this.id = id;
        this.listenPort = listenPort;
    }
    public void setCoToServer(ConnectionObject co){
        this.coToServer = co;
    }
    public ConnectionObject getCoToServer() {
        return coToServer;
    }
    
    public void start(){
        Thread t = new Thread(()-> startClient(serverAddress.getIp(),serverAddress.getPort()));
        Thread t1 = new Thread(()-> startLitterServer());
        t.start();
        t1.start();
    }
    public Channel startClient(String host, int port){
        EventLoopGroup group = new NioEventLoopGroup(8);
        Channel ch = null;
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());
            b.option(ChannelOption.SO_KEEPALIVE,true);
            b.option(ChannelOption.SO_REUSEADDR, true);
            this.b =b;
            ChannelFuture future  = b.connect(host,port).sync();
            if(future.isSuccess()){
                ch = future.channel();
                InetSocketAddress  address = (InetSocketAddress)ch.localAddress();
                myAddress  = new Address(address.getHostString(),address.getPort());
                UserMap.addAddr(myAddress,this);
                coToServer = new ConnectionObject();
                coToServer.setChannel(ch);
                // report to the server my listen port
                reportPort();
            }
            startPing();
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace( );
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
            return ch;
        }
        
    }
    
    public void startLitterServer(){
        ServerBootstrap bootstrap;
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ClientInitializer());
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        try {
            ChannelFuture cf = bootstrap.bind(localhost,listenPort).sync();
            UserMap.addAddr(new Address(localhost,listenPort),this);
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }
    }
    
    public  ConnectionObject connet(long id, String Ip, int port){
        SocketAddress sa = new InetSocketAddress(Ip,port);
        Channel channel = null ;
        Address address = new Address(Ip,port);
        if(coMap.containsKey(address)){
             return coMap.get(address);
        }
        try {
            ChannelFuture future = b.connect(sa).sync();
            
            if(future.isSuccess()){
                channel = future.channel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }
        ConnectionObject co = new ConnectionObject();
        co.setChannel(channel);
        CCPingMsg ccPingMsg = new CCPingMsg();
        co.sendMessage(ccPingMsg);
        coMap.put(address,co);
        addrMap.put(id, address);
        return co;
    }
    
    public void startPing(){
        Runnable runnable = () -> {
            CSPingMsg msg = new CSPingMsg();
            msg.setCharId(id);
            coToServer.sendMessage(msg); // not sure that this object is already sent , cause main thread is shutdown
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable,0,5, TimeUnit.SECONDS);
    }
    
    // report to the server my listen port
    public void reportPort(){
        CSPortReportMsg msg = new CSPortReportMsg(this.id,this.localhost,this.listenPort);
        coToServer.sendMessage(msg);
    }
    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }
    public int getListenPort(){
        return this.listenPort;
    }
}