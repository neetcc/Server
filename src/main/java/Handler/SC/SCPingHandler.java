package Handler.SC;

import Connection.ConnectionObject;
import Connection.Sender;
import TaskManagement.AbstractSimpleTask;
import TaskManagement.DefaultTaskManager;
import constant.MsgConstant;
import db.Entity.User;
import db.Table.UserTable;
import msg.AbstractMessageHandler;
import msg.SC.SCPingMsg;
import server.ServerConfig;

/**
 * Created by ChengCe on 2017/12/4.
 */
public class SCPingHandler extends AbstractMessageHandler<SCPingMsg, Sender> {
    private DefaultTaskManager taskManager = ServerConfig.taskManager;
    private UserTable ut = ServerConfig.ut;
    @Override
    protected void doExecute(SCPingMsg msg, Sender sender) {

        taskManager.executeITask(new AbstractSimpleTask( ) {
            @Override
            protected void execute() {
                System.out.println(msg.getId() + " send to " + ((ConnectionObject)sender).getChannel().remoteAddress());
                //ut.insert(new User(msg.getId(),String.valueOf(msg.getId())));
            }

            @Override
            protected void callback() {
                System.out.println();
            }
        });


    }

    @Override
    public int getMessageIds() {
        return MsgConstant.SC_PING_MSG;
    }
}
