package client;

import Connection.ConnectionObject;
import constant.MsgHandlerLoader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import msg.CS.CSPingMsg;
import msg.ThriftMsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class Client {
    public static String host = "127.0.0.1";
    public static int port = 7878;
    public void starClinet()  throws InterruptedException, IOException{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());

            // 连接服务端
            Channel ch = b.connect(host, port).sync().channel();
            ConnectionObject CO = new ConnectionObject();
            CO.setChannel(ch);
           // MsgHandlerLoader.loadHandler();
            for(int i = 0;i<50;i++){
                CSPingMsg msg = new CSPingMsg();
                msg.setCharId(i);
                CO.sendMessage(msg); // not sure that this object is already sent , cause main thread is shutdown
            }
            Thread.currentThread().sleep(1500);
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }

}