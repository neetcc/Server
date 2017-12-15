package client;

import Connection.ConnectionObject;
import constant.MsgHandlerLoader;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import msg.CS.CSPingMsg;
import msg.SC.SCPingMsg;
import msg.ThriftMsg;
import server.ServerConfig;
import server.ServerInitializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class Client {
    public  String host = ServerConfig.IP;
    public  int port = ServerConfig.PORT;
    public Channel chToServer;
    public ConnectionObject coToServer;
    public SocketAddress myport;
    public int liport;
    Bootstrap b;
    private final long id ;
    public Client(long id){
        this.id = id;
    }
    public void setPort(SocketAddress port){
        this.myport = port;
    }
    public void start(){
        Thread t = new Thread(()->{
            starClinet(host,port);
        });
        t.start();
    }
    public Channel starClinet(String host, int port){
        boolean isServer = (host.equals(this.host)&& port == this.port) ? true: false;
        EventLoopGroup group = new NioEventLoopGroup();
        Channel ch = null;
        try {
            Bootstrap b = new Bootstrap();
            ServerBootstrap bootstrap;
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());
            b.option(ChannelOption.SO_KEEPALIVE,true);
            // 连接服务端
            ChannelFuture future = null;
            this.b =b;
            future = b.connect(host,port).sync();
            if(isServer) {
                bootstrap = new ServerBootstrap();
                bootstrap.group(group);
                bootstrap.channel(NioServerSocketChannel.class);
                bootstrap.childHandler(new ClientInitializer());
                bootstrap.bind(liport).sync();
            }
            if(future.isSuccess()){
                ch = future.channel();
                myport = ch.localAddress();
                if(isServer) chToServer = ch;
            }
           // MsgHandlerLoader.loadHandler();
            if(isServer)
            startping(chToServer);
        } catch (InterruptedException e) {
            e.printStackTrace( );
        } finally {
            // The connection is closed automatically on shutdown.
           // group.shutdownGracefully();
            return ch;
        }
        
    }
    
    public Channel newLink(String Ip, int port){
           Channel ch =  starClinet(Ip,8002);
           return ch;
    }
    
    public  ConnectionObject connet(String Ip, int port){
        SocketAddress sa = new InetSocketAddress(Ip,8002);
        Channel channel = null ;
        try {
           channel = b.connect(sa).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }
        ConnectionObject ob = new ConnectionObject();
        ob.setChannel(channel);
        return ob;
    }
    
    public void startping(Channel ch){
        coToServer = new ConnectionObject();
        coToServer.setChannel(ch);
        Runnable runnable = () -> {
            CSPingMsg msg = new CSPingMsg();
            msg.setCharId(id);
            coToServer.sendMessage(msg); // not sure that this object is already sent , cause main thread is shutdown
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable,0,1, TimeUnit.SECONDS);
    }

}