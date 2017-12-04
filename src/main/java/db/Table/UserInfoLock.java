package db.Table;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChengCe on 2017/12/4.
 */
public class UserInfoLock {
    private static Map<Long, Object> locks = new HashMap<>();
    /*
    use as 
     synchronized(userinfolock.get(userid)){
     }
     */
    public Object get(long id){
        if(!locks.containsKey(id)){
            locks.put(id,new Object());
        }
        
        return locks.get(id);
    }
}
