package Handler.CS;

import Connection.Sender;
import client.Addr;
import constant.MsgConstant;
import msg.AbstractMessageHandler;
import msg.CS.CSPortReportMsg;
import server.UserMap;

/**
 * Created by ChengCe on 2017/12/18.
 */
public class CSPortReportMsgHandler extends AbstractMessageHandler<CSPortReportMsg,Sender> {
    @Override
    protected void doExecute(CSPortReportMsg msg, Sender sender) {
        UserMap.addUserAddr(msg.getId(),new Addr(msg.getIp(),msg.getMyListenPort()));
        System.out.println(msg.getId() + " report its listening port on : " + msg.getMyListenPort());
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CS_PORT_REPORT_MSG;
    }
}
