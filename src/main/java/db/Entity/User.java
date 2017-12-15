package db.Entity;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by ChengCe on 2017/12/4.
 */
public class User implements Serializable {
    private long id;
    private String name;
   // public ByteBuffer bytes;
    public User(){
        
    }
    public User(long id, String name){
        this.id = id;
        this.name = name;
    }
    
//    public User(long id,String name , ByteBuffer buffer){
//        this.id = id;
//        this.name = name;
//        this.bytes = buffer;
//    }
    public String getName(){
        return name;
    }
    public long getId(){
        return id;
    }
}
