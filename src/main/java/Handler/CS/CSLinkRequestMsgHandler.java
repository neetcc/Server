package Handler.CS;

import Connection.ConnectionObject;
import Connection.Sender;
import client.Address;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.CS.CSLinkRequestMsg;
import msg.SC.SCLinkRequestMsg;
import server.UserMap;


/**
 * Created by ChengCe on 2017/12/15.
 */
public class CSLinkRequestMsgHandler  extends AbstractMessageHandler<CSLinkRequestMsg,Sender> {
    @Override
    protected void doExecute(CSLinkRequestMsg msg, Sender sender) {
        Address remoteAddress = UserMap.getUserAddr(msg.getOtherId());
        // tell the requester the address of its request
        SCLinkRequestMsg SCmsg =new SCLinkRequestMsg(msg.getOtherId(),remoteAddress.getIp(),remoteAddress.getPort());
        ConnectionObject co = (ConnectionObject) sender;
        co.sendMessage(SCmsg);
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CS_LINK_REQUEST_MSG;
    }
    
}
