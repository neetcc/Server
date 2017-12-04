package Handler.SC;

import Connection.Sender;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.SC.SCPingMsg;

/**
 * Created by ChengCe on 2017/12/4.
 */
public class SCPingHandler extends AbstractMessageHandler<SCPingMsg, Sender> {
    @Override
    protected void doExecute(SCPingMsg msg, Sender sender) {
        System.out.println(msg.getId());
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.SC_PING_MSG;
    }
}
