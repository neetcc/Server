package db.Table;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import db.Entity.User;
import db.MongoDaoImpl;
import db.MongoHelper;
import db.MongoUtil;
import org.bson.Document;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ChengCe on 2017/12/4.
 */
public abstract class AbstractTable<T> {
    
    protected String tableName;
    protected MongoHelper mongoHelper;
    protected MongoClient mongoClient;
    protected MongoDatabase mongoDataBase;
    protected MongoDaoImpl mongoDaoImpl;
    protected Class<T> entityClass;
    private Gson gson;
    protected void dbInit(){
         mongoHelper = new MongoHelper();
         mongoClient = mongoHelper.getMongoClient();
         mongoDataBase = mongoHelper.getMongoDataBase(mongoClient);
         mongoDaoImpl = new MongoDaoImpl();
         entityClass = getclass();
         gson = new Gson();
    }
    
    public Class getclass(){
        Class<T>  entityClass = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }
    
    public Set<T> queryByID(long id){
        Set<T> res = new HashSet<>();
        Set<Document> set = mongoDaoImpl.queryByID(mongoDataBase,tableName,id);
        for(Document d :set){
          res.add(gson.fromJson(d.toJson(),entityClass));
        }
        return res;
    }
    
    public void insert(T objcet){
        mongoDaoImpl.insert(mongoDataBase,tableName,objcet);
    }
    
    public void deleteById(long id){
        BasicDBObject query = new BasicDBObject("_id",id);
        MongoCollection<Document> collection = mongoDataBase.getCollection(tableName);
        collection.deleteMany(query);
    }
    
    public void delete(T object){
        mongoDaoImpl.delete(mongoDataBase,tableName,object);
    }
    
    public void updateById(long id, T object){
        BasicDBObject where = new BasicDBObject("_id",id);
        MongoCollection<Document> collection = mongoDataBase.getCollection(tableName);
        BasicDBObject newContent = new BasicDBObject();
        MongoUtil.getInstance().dymParms(newContent, object, "append");
        BasicDBObject update = new BasicDBObject("$set",newContent);
        collection.updateOne(where,update);
    }
    
    public void drop(){
        MongoCollection<Document> collection = mongoDataBase.getCollection(tableName);
        collection.drop();
    }
    
    public abstract void update(T object);
     
}
