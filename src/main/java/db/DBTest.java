package db;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.Entity.User;
import db.Table.UserTable;
import org.bson.Document;

import java.util.Set;

/**
 * Created by ChengCe on 2017/12/4.
 */
public class DBTest {
    public static void main(String[] args) throws InterruptedException {
      UserTable ut = new UserTable();
      ut.drop();
      ut.insert(new User(1,"first"));
      ut.insert(new User(2,"second"));
      ut.insert(new User(3,"third"));
      Thread.currentThread().sleep(15000);
      Set<User> u = ut.queryByID(1);
      for(User ser :u){
          System.out.println(ser.getName());
      }
        Thread.currentThread().sleep(15000);
      ut.deleteById(1);
        Thread.currentThread().sleep(15000);
        ut.delete(new User(2,"second"));
        Thread.currentThread().sleep(15000);
        ut.updateById(3,new User(3,"three"));


    }
}
