package Handler.CS;

import Connection.ConnectionObject;
import Connection.Sender;
import TaskManagement.AbstractSimpleTask;
import TaskManagement.DefaultTaskManager;
import constant.MsgConstant;
import db.Table.UserTable;
import msg.AbstractMessageHandler;
import msg.CS.CSPingMsg;
import msg.SC.SCPingMsg;
import server.ServerConfig;
import server.UserMap;

import java.net.InetSocketAddress;

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
                InetSocketAddress local =(InetSocketAddress) ((ConnectionObject)sender).getChannel().localAddress();
                InetSocketAddress remote =(InetSocketAddress) ((ConnectionObject)sender).getChannel().remoteAddress();
                System.out.println(local + "get ping msg from : id: "+msg.getCharId() + " : " + remote);
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
