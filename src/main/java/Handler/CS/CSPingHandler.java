package Handler.CS;

import Connection.ConnectionObject;
import Connection.Sender;
import TaskManagement.AbstractSimpleTask;
import TaskManagement.DefaultTaskManager;
import client.Client;
import constant.MsgConstant;
import db.Entity.User;
import db.Table.UserTable;
import msg.AbstractMessageHandler;
import msg.CS.CSPingMsg;
import msg.SC.SCPingMsg;
import server.ServerConfig;
import server.UserMap;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class CSPingHandler extends AbstractMessageHandler<CSPingMsg,Sender> {
    private DefaultTaskManager taskManager = ServerConfig.taskManager;
    private UserTable ut = ServerConfig.ut;
    @Override
    protected void doExecute(CSPingMsg msg, Sender sender) {

        UserMap.addUser(msg.getCharId(),(ConnectionObject) sender);
        
        taskManager.executeITask(new AbstractSimpleTask( ) {
            @Override
            protected void execute() {
                System.out.println(msg.getCharId());
                //ut.insert(new User(msg.getCharId(),String.valueOf(msg.getCharId())));
                ConnectionObject CO = (ConnectionObject) sender;
                SCPingMsg msgss = new SCPingMsg();
                msgss.setId(msg.getCharId()+100);
                CO.sendMessage(msgss); // not sure that this object is already sent , cause main thread is shutdown
            }

            @Override
            protected void callback() {
                System.out.println();
            }
        });
        
        
    }

    @Override
    public int getMessageIds() {
        return MsgConstant.CS_PING_MSG;
    }
}
