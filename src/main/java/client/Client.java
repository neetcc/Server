package client;

import Connection.ConnectionObject;
import constant.MsgHandlerLoader;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import msg.CS.CSPingMsg;
import msg.IMessage;
import msg.ThriftMsg;
import org.apache.thrift.TBase;
import server.ServerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class Client {
    public static String host = ServerConfig.IP;
    public static int port = ServerConfig.PORT;
    private long id;
    private ConnectionObject CO;

    public ConnectionObject getCO() {
        return CO;
    }

    public void setCO(ConnectionObject CO) {
        this.CO = CO;
    }

    public Client(){
        
    }
    public Client(long id){
        this.id = id;
    }
    public long getId(){return this.id;}
    public void setId(long id){this.id = id;}
    public void starClinet()  throws InterruptedException, IOException{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_RCVBUF, 1024 * 64)
                    .option(ChannelOption.SO_SNDBUF, 1024 * 64);
            b.handler(new ClientInitializer());
            // 连接服务端
            Channel ch = b.connect(host, port).sync().channel();
            ConnectionObject CO = new ConnectionObject();
            CO.setChannel(ch);
           // MsgHandlerLoader.loadHandler();
            hearbeat(CO);
        } finally {
            // The connection is closed automatically on shutdown.
            //group.shutdownGracefully();
        }
    }
    
    public void send(TBase<?,?> msg){
        this.CO.sendMessage(msg);
    }
    private void hearbeat(ConnectionObject connectionObject){
        Runnable runnable = () -> {
            CSPingMsg msg = new CSPingMsg();
            msg.setCharId(id);
            connectionObject.sendMessage(msg);
        };
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(runnable,10,10, TimeUnit.SECONDS);
    }

}