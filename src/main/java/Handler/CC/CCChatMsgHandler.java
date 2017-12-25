package Handler.CC;

import Connection.ConnectionObject;
import Connection.Sender;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.CC.CCChatMsg;

import java.net.InetSocketAddress;

/**
 * Created by ChengCe on 2017/12/18.
 */
public class CCChatMsgHandler extends AbstractMessageHandler<CCChatMsg,Sender> {
    @Override
    protected void doExecute(CCChatMsg msg, Sender sender) {
        ConnectionObject player = ((ConnectionObject)sender);
        InetSocketAddress address = (InetSocketAddress) player.getChannel().localAddress();
        //MAddress myAddr = new MAddress(address.getHostString(),address.getPort());
        InetSocketAddress remoteAddress = (InetSocketAddress)player.getChannel().remoteAddress();
        //MAddress reAddr = new MAddress(remoteAddress.getHostString(),remoteAddress.getPort());
        System.out.println(address + "receive ccmsg from " + remoteAddress + "     : " + msg.getContent());
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CC_CHAT_MSG;
    }
}
