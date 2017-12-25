package Handler.SC;

import Connection.Sender;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.SC.SCRequestSerInfo;

/**
 * Created by ChengCe on 2017/12/19.
 */
public class SCRequestSerInfoHandler extends AbstractMessageHandler<SCRequestSerInfo,Sender> {
    @Override
    protected void doExecute(SCRequestSerInfo msg, Sender sender) {
        
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.SC_REQUEST_SERINFO;
    }
}
