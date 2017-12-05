package server;

import Handler.HandlerTask;
import TaskManagement.DefaultTaskManager;
import db.Table.UserTable;

/**
 * Created by ChengCe on 2017/12/5.
 */
public class ServerConfig {
    public static final DefaultTaskManager taskManager = new DefaultTaskManager();
    public static final UserTable ut = new UserTable();
    public static final HandlerTask ht = new HandlerTask();
    public static final String IP = "127.0.0.1";
    public static final int PORT = 7878;
}
