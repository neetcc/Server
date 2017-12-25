package server;

import Connection.ConnectionObject;
import client.MAddress;
import client.Client;
import org.apache.commons.collections.map.HashedMap;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChengCe on 2017/12/15.
 */
public class UserMap {
    private static Map<Long, ConnectionObject> UserMap = new HashMap<>(); // id -- co
    private static Map<MAddress, Client> userMap = new HashedMap(); // address -- client
    private static Map<Long, Long> UserLastLog = new HashMap<>();// last time user ping the server
    private static Map<Long, MAddress> UserAddr = new HashedMap(); // id -- address
    public static void addUserAddr(long Id, MAddress MAddress){
        UserAddr.put(Id, MAddress);
    }
    public static MAddress getUserAddr(long id){
        if(UserAddr.containsKey(id)){
            return UserAddr.get(id);
        }else{
            return  null;
        }
    }
    public static void addUser(Long id, ConnectionObject Co){
        UserMap.put(id, Co);
        UserLastLog.put(id, System.currentTimeMillis());
    }
    public static void addAddr(MAddress MAddress, Client client){
        userMap.put(MAddress,client);
    }
    public static Client getClient(MAddress MAddress){
        return userMap.get(MAddress);
    }
    public static SocketAddress getUserClient(long id){
        if(UserMap.containsKey(id)){
            return UserMap.get(id).getChannel().remoteAddress();
        }else{
            return null;
        }
    }
    
    
}
