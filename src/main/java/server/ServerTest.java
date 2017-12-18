package server;

import client.ChatSer;
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
        Client client1 = new Client(1,8001);
        Client client2 = new Client(2,8002);
        client1.start();
        client2.start();
        Thread.currentThread().sleep(4000);
        CSLinkRequestMsg msg = new CSLinkRequestMsg(1);
        client2.getCoToServer().sendMessage(msg);
        Thread.currentThread().sleep(5000);
        msg.setOtherId(2);
        client1.getCoToServer().sendMessage(msg);
        Thread.currentThread().sleep(5000);
        ChatSer chatSer1 = new ChatSer(client1);
        ChatSer chatSer2 = new ChatSer(client2);
        chatSer1.chatWithChar(2, "hello two");
        
        Thread.currentThread().sleep(2000);
        chatSer2.chatWithChar(1, "hello one");
        Thread.currentThread().sleep(2000);
        chatSer1.chatWithChar(2, "hello two");
    }
}
