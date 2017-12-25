package Handler.CC;

import Connection.ConnectionObject;
import Connection.Sender;
import client.MAddress;
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
        MAddress myMAddress = new MAddress(address.getHostString(),address.getPort());
        InetSocketAddress remoteAddress = (InetSocketAddress) player.getChannel().remoteAddress(); 
        MAddress reMAddress = new MAddress(remoteAddress);
        Client client = UserMap.getClient(myMAddress);
        // add the co to the client's map each time receiving a ping msg 
        client.addOtherCO(reMAddress,(ConnectionObject) sender);
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CC_PING_MSG;
    }
}
