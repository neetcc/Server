package Handler.CS;

import Connection.ConnectionObject;
import Connection.Sender;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.CS.CSLinkRequestMsg;
import msg.SC.SCLinkRequestMsg;
import server.UserMap;

import java.net.InetSocketAddress;


/**
 * Created by ChengCe on 2017/12/15.
 */
public class CSLinkRequestMsgHandler  extends AbstractMessageHandler<CSLinkRequestMsg,Sender> {
    @Override
    protected void doExecute(CSLinkRequestMsg msg, Sender sender) {
        InetSocketAddress sa = (InetSocketAddress) UserMap.getUserAddr(msg.getOtherId());
        SCLinkRequestMsg SCmsg =new SCLinkRequestMsg(msg.getOtherId(),sa.getHostString(),String.valueOf(sa.getPort()));
        ConnectionObject co = (ConnectionObject) sender;
        co.sendMessage(SCmsg);
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CS_LINKREQUEST_MSG;
    }
    
}
