package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import msg.CS.CSPingMsg;
import msg.ThriftMsg;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class ClientHandler extends SimpleChannelInboundHandler<ThriftMsg> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ThriftMsg msg) throws Exception {
        CSPingMsg ms = (CSPingMsg) msg.getMessage(); 
        System.out.println(ms.getCharId());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client active ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client close ");
        super.channelInactive(ctx);
    }
}