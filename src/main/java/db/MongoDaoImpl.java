package db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;

/**
 * Created by ChengCe on 2017/12/4.
 */
public class MongoDaoImpl implements MongoDao{
    @Override
    public Set<Document> queryByID(MongoDatabase db, String table, long Id) {
        MongoCollection<Document> collection = db.getCollection(table);
        BasicDBObject query = new BasicDBObject("_id", Id);
        FindIterable<Document> iterable = collection.find(query);
        MongoCursor<Document> cursor = iterable.iterator();
        Set<Document> res = new HashSet<>();
        while (cursor.hasNext()) {
            Document user = cursor.next();
            res.add(user);
        }
        cursor.close();
        return res;
    }

    // test insert  

    @Override
    public void insert(MongoDatabase db,String table,Object obj) {
        MongoCollection<Document> collection = db.getCollection(table);
        Document doc = new Document();
        MongoUtil.getInstance().dymParms(doc, obj,"put");
        collection.insertOne(doc);
        FindIterable<Document> iterable = collection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document user = cursor.next();
           // System.out.println(user.toString());
        }

    }

    @Override
    public void delete(MongoDatabase db,String table,Object obj) {
        MongoCollection<Document> collection = db.getCollection(table);
        BasicDBObject query = new BasicDBObject();
        MongoUtil.getInstance().dymParms(query, obj, "append");
        collection.deleteMany(query);
        FindIterable<Document> iterable = collection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document user = cursor.next();
          //  System.out.println(user.toString());
        }
    }


    @Override
    public void update(MongoDatabase db,String table,Object conditions,Object obj) {
        MongoCollection<Document> collection = db.getCollection(table);
        BasicDBObject where = new BasicDBObject();
        MongoUtil.getInstance().dymParms(where, conditions, "append");
        BasicDBObject newContent = new BasicDBObject();
        MongoUtil.getInstance().dymParms(newContent, obj, "append");
        BasicDBObject update = new BasicDBObject("$set",newContent);
        //updateMany updateOne   
        collection.updateMany(where, update);
        FindIterable<Document> iterable = collection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document user = cursor.next();
           // System.out.println(user.toString());
        }
    }


}
