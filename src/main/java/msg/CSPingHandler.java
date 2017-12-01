package msg;

import Connection.Sender;
import constant.MsgConstant;
import msg.CS.CSPingMsg;
import org.apache.thrift.TBase;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class CSPingHandler extends AbstractMessageHandler<CSPingMsg,Sender>{
    @Override
    protected void doExecute(CSPingMsg msg, Sender sender) {
        System.out.println(msg.getCharId());
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CS_PING_MSG;
    }
}
