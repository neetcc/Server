package server;

import Connection.ConnectionObject;
import client.Addr;
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
    private static Map<Addr, Client> userMap = new HashedMap();
    private static Map<Long, Long> UserLastLog = new HashMap<>();
    private static Map<Long, Addr> UserAddr = new HashedMap();
    public static void addUserAddr(long Id, Addr addr){
        UserAddr.put(Id, addr);
    }
    public static Addr getUserAddr(long id){
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
    public static void addAddr(Addr addr, Client client){
        userMap.put(addr,client);
    }
    public static Client getClient(Addr addr){
        return userMap.get(addr);
    }
    public static SocketAddress getUserClient(long id){
        if(UserMap.containsKey(id)){
            return UserMap.get(id).getChannel().remoteAddress();
        }else{
            return null;
        }
    }
    
    
}
