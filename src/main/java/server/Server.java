package server;

import client.Client;
import constant.MsgHandlerLoader;
import db.Table.UserTable;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class Server {
    /**
     * 服务端监听的端口地址
     */
    private static final int portNumber = ServerConfig.PORT;

    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ServerInitializer());
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(portNumber).sync();
            
            MsgHandlerLoader.loadHandler();
            ServerTest.test();
            
            
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
            
            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

