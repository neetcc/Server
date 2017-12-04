package db;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Set;

/**
 * Created by ChengCe on 2017/12/4.
 */
public interface MongoDao {
    /**
     * Get Data BY ID 
     * @param db
     * @param table
     * @param Id
     */
    Set<Document> queryByID(MongoDatabase db, String table, long Id);

    /**
     * Insert Data 
     * @param db
     * @param table
     * @param obj
     */
     void insert(MongoDatabase db, String table, Object obj);

    /**
     * Delete Data 
     * @param db
     * @param table
     * @param obj
     */
     void delete(MongoDatabase db, String table, Object obj);

    /**
     * Update Data 
     * @param db
     * @param table
     * @param conditions
     * @param obj
     */
     void update(MongoDatabase db, String table, Object conditions,
                       Object obj);

}
