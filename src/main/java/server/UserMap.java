package server;

import Connection.ConnectionObject;
import client.Client;
import org.apache.commons.collections.map.HashedMap;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChengCe on 2017/12/15.
 */
public class UserMap {
    private static Map<Long, ConnectionObject> UserMap = new HashMap<>();
    private static Map<Long, Long> UserLastLog = new HashMap<>();
    private static Map<Long, Client> ClientMap = new HashedMap();
    public static void addUser(Long id, ConnectionObject Co){
        UserMap.put(id, Co);
        UserLastLog.put(id, System.currentTimeMillis());
    }
    
    public static void addClient(long id, Client client){
        ClientMap.put(id,client);
    }
    public static Client getClient(long id){
        return ClientMap.get(id);
    }
    public static SocketAddress getUserAddr(long id){
        if(UserMap.containsKey(id)){
            return UserMap.get(id).getChannel().remoteAddress();
        }else{
            return null;
        }
    }
    
    
}
