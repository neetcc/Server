package server;

import Connection.ConnectionObject;
import constant.MsgHandlerLoader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import msg.CS.CSPingMsg;
import msg.IMessageHandler;
import msg.SC.SCPingMsg;
import msg.ThriftMsg;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ChengCe on 2017/12/1.
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {
  
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ThriftMsg  msgs = (ThriftMsg)msg;
        ConnectionObject CO = new ConnectionObject();
        CO.setChannel(ctx.channel());
        msgs.setSender(CO);
        IMessageHandler iMessageHandler = MsgHandlerLoader.getMsgHandler(msgs.getMessageId());
        iMessageHandler.execute(msgs);
    }

    /*
     * 
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * 
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        
       // ctx.writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

        super.channelActive(ctx);
    }
    
    
}