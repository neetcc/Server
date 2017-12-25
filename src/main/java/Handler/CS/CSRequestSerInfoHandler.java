package Handler.CS;

import Connection.Sender;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.CS.CSRequestSerInfo;

/**
 * Created by ChengCe on 2017/12/19.
 */
public class CSRequestSerInfoHandler extends AbstractMessageHandler<CSRequestSerInfo,Sender> {
    @Override
    protected void doExecute(CSRequestSerInfo msg, Sender sender) {
        
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CS_REQUEST_SERINFO;
    }
}
