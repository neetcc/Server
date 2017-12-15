package Handler.SC;

import Connection.ConnectionObject;
import Connection.Sender;
import client.Client;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.SC.SCLinkRequestMsg;
import msg.SC.SCPingMsg;
import server.UserMap;

/**
 * Created by ChengCe on 2017/12/15.
 */
public class SCLinkRequestMsgHandler extends AbstractMessageHandler<SCLinkRequestMsg, Sender>{
    @Override
    protected void doExecute(SCLinkRequestMsg msg, Sender sender) {
       ConnectionObject co = UserMap.getClient(1).connet(msg.getIp(),Integer.valueOf(msg.getPort()));
        SCPingMsg SCmsg= new SCPingMsg();
        SCmsg.setId(1);
        co.sendMessage(SCmsg);
        
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.SC_LINK_REQUEST_MSG;
    }
}
