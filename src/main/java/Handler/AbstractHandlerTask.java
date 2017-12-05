package Handler;

import Connection.Sender;
import TaskManagement.AbstractSimpleTask;
import TaskManagement.DefaultTaskManager;
import db.Table.UserTable;
import server.ServerConfig;

/**
 * Created by ChengCe on 2017/12/5.
 */
public abstract class AbstractHandlerTask {
    private DefaultTaskManager taskManager = ServerConfig.taskManager;
    private UserTable ut = ServerConfig.ut;
    public void generateUser(Sender sender){
        taskManager.executeITask(new AbstractSimpleTask( ) {
            long newId;
            @Override
            protected void execute() {
                System.out.println("execute");
            }

            @Override
            protected void callback() {
               newId = 1;
               onGenerateUser(newId, sender);
            }
        });
    }
    
    protected abstract void onGenerateUser(long id,Sender sender);
}
