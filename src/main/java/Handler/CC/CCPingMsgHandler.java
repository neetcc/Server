package Handler.CC;

import Connection.ConnectionObject;
import Connection.Sender;
import client.Addr;
import client.Client;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.CC.CCPingMsg;
import server.UserMap;

import java.net.InetSocketAddress;

/**
 * Created by ChengCe on 2017/12/18.
 */
public class CCPingMsgHandler extends AbstractMessageHandler<CCPingMsg,Sender> {
    @Override
    protected void doExecute(CCPingMsg msg, Sender sender) {
        ConnectionObject player = ((ConnectionObject)sender);
        InetSocketAddress address = (InetSocketAddress) player.getChannel().localAddress();
        Addr myAddr = new Addr(address.getHostString(),address.getPort());
        InetSocketAddress remoteAddress = (InetSocketAddress) player.getChannel().remoteAddress(); 
        Addr reAddr = new Addr(remoteAddress);
        Client client = UserMap.getClient(myAddr);
        client.addOtherCO(reAddr,(ConnectionObject) sender);
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CC_PING_MSG;
    }
}
