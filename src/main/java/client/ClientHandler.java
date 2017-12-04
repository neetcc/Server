package client;

import Connection.ConnectionObject;
import constant.MsgHandlerLoader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import msg.CS.CSPingMsg;
import msg.IMessageHandler;
import msg.ThriftMsg;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Object> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ThriftMsg  msgs = (ThriftMsg)msg;
        ConnectionObject CO = new ConnectionObject();
        CO.setChannel(ctx.channel());
        msgs.setSender(CO);
        IMessageHandler iMessageHandler = MsgHandlerLoader.getMsgHandler(msgs.getMessageId());
        iMessageHandler.execute(msgs);
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