package server;

import client.Client;
import db.Table.UserTable;
import msg.CS.CSLinkRequestMsg;

import java.io.IOException;

/**
 * Created by ChengCe on 2017/12/7.
 */
public class ServerTest {
    public static void test() throws IOException, InterruptedException {
        UserTable ut = ServerConfig.ut;
        ut.drop();
        Client client1 = new Client(1);
        Client client2 = new Client(2);
        client1.liport = 8001;
        client1.liport = 8002;
        UserMap.addClient(1,client1);
        UserMap.addClient(2,client2);
        client1.start();
        client2.start();
        Thread.currentThread().sleep(4000);
        CSLinkRequestMsg msg = new CSLinkRequestMsg(1);
        client2.coToServer.sendMessage(msg);
        
    }
}
