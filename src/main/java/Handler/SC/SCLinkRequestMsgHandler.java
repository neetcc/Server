package Handler.SC;

import Connection.ConnectionObject;
import Connection.Sender;
import client.Address;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.SC.SCLinkRequestMsg;
import server.UserMap;

import java.net.InetSocketAddress;

/**
 * Created by ChengCe on 2017/12/15.
 */
public class SCLinkRequestMsgHandler extends AbstractMessageHandler<SCLinkRequestMsg, Sender>{
    @Override
    protected void doExecute(SCLinkRequestMsg msg, Sender sender) {
       
       ConnectionObject player = ((ConnectionObject)sender);
       InetSocketAddress address = (InetSocketAddress) player.getChannel().localAddress();
       Address myAddress = new Address(address.getHostString(),address.getPort());
       //ConnectionObject co = 
               UserMap.getClient(myAddress).connet(msg.getOtherId(),msg.getIp(),msg.getPort());
       System.out.println(UserMap.getClient(myAddress).getId() + " is connecting to :" + msg.getIp() +" : " + msg.getPort());
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.SC_LINK_REQUEST_MSG;
    }
}
