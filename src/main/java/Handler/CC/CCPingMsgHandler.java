package Handler.CC;

import Connection.ConnectionObject;
import Connection.Sender;
import client.Address;
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
        Address myAddress = new Address(address.getHostString(),address.getPort());
        InetSocketAddress remoteAddress = (InetSocketAddress) player.getChannel().remoteAddress(); 
        Address reAddress = new Address(remoteAddress);
        Client client = UserMap.getClient(myAddress);
        // add the co to the client's map each time receiving a ping msg 
        client.addOtherCO(reAddress,(ConnectionObject) sender);
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CC_PING_MSG;
    }
}
