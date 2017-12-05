package server;

import TaskManagement.DefaultTaskManager;
import db.Table.UserTable;

/**
 * Created by ChengCe on 2017/12/5.
 */
public class ServerConfig {
    public static final DefaultTaskManager taskManager = new DefaultTaskManager();
    public static final UserTable ut = new UserTable();
}
