package Handler.CS;

import Connection.ConnectionObject;
import Connection.Sender;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.CS.CSPingMsg;
import msg.SC.SCPingMsg;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class CSPingHandler extends AbstractMessageHandler<CSPingMsg,Sender> {
    @Override
    protected void doExecute(CSPingMsg msg, Sender sender) {
        System.out.println(msg.getCharId());
        ConnectionObject CO = (ConnectionObject) sender;
        SCPingMsg msgss = new SCPingMsg();
        msgss.setId(msg.getCharId()+100);
        CO.sendMessage(msgss); // not sure that this object is already sent , cause main thread is shutdown
        
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CS_PING_MSG;
    }
}
