package Handler;

import Connection.ConnectionObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ChengCe on 2017/12/5.
 */
public class UserManagement {
    private static Map<Long , ConnectionObject> userMap = new ConcurrentHashMap<>();
    public void updateConnectOb(long id, ConnectionObject connectionObject){
        userMap.put(id,connectionObject);
    }
    public ConnectionObject getCO(long id){
        if(userMap.containsKey(id))
        return userMap.get(id);
        else return null;
    }

    public static Map<Long, ConnectionObject> getUserMap() {
        return userMap;
    }
}
