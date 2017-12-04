package db.Entity;

import java.io.Serializable;

/**
 * Created by ChengCe on 2017/12/4.
 */
public class User implements Serializable {
    private long id;
    private String name;
    public User(){
        
    }
    public User(long id, String name){
        this.id = id;
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public long getId(){
        return id;
    }
}
