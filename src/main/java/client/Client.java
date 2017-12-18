package client;

import Connection.ConnectionObject;
import com.sun.org.apache.regexp.internal.RE;
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
    private Addr serverAddress = new Addr(ServerConfig.IP, ServerConfig.PORT);
    private Channel chToServer;
    private ConnectionObject coToServer;
    private Addr myAddress;
    private int listenPort;
    private String localhost = "127.0.0.1";
    public void setLocalhost(String localhost){
        this.localhost = localhost;
    }
    public String getLocalhost(){
        return  this.localhost;
    }
    private Map<Addr, ConnectionObject> coMap = new HashedMap();
    private Map<Long, Addr> addrMap = new HashedMap();
    public void addOtherAddr(Long id, Addr addr){
        addrMap.put(id, addr);
    }
    public Addr getOtherAddr(Long id){
        if(addrMap.containsKey(id)){
            return addrMap.get(id);
        }else{
            return  null;
        }
    }
    public void addOtherCO(Addr addr, ConnectionObject CO){
        coMap.put(addr,CO);
    }
    public ConnectionObject getOtherCO(Addr addr){
        if(coMap.containsKey(addr)){
            return coMap.get(addr);
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
                myAddress  = new Addr(address.getHostString(),address.getPort());
                UserMap.addAddr(myAddress,this);
                chToServer =ch;
                coToServer = new ConnectionObject();
                coToServer.setChannel(ch);
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
            UserMap.addAddr(new Addr(localhost,listenPort),this);
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }
    }
    
    public  ConnectionObject connet(long id, String Ip, int port){
        SocketAddress sa = new InetSocketAddress(Ip,port);
        Channel channel = null ;
        Addr addr = new Addr(Ip,port);
        if(coMap.containsKey(addr)){
             return coMap.get(addr);
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
        coMap.put(addr,co);
        addrMap.put(id, addr);
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