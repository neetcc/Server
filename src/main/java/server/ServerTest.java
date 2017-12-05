package server;

import client.Client;

import java.io.IOException;

/**
 * Created by ChengCe on 2017/12/5.
 */
public class ServerTest {
    public static void test(){
        Thread td = new Thread(() -> {
            Client[] client = new Client[10];
            try {
                
                
                for(int i =0 ; i< client.length;i++){
                    client[i] = new Client(i);
                    client[i].starClinet();
                }
                
                
                
                
                
                
            } catch (InterruptedException e) {
                e.printStackTrace( );
            } catch (IOException e) {
                e.printStackTrace( );
            }
        });

        td.start();
    }
}
