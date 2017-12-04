package db;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by ChengCe on 2017/12/4.
 */
public class MongoHelper {
    static final String DBName = DBConstant.DBNAME;
    static final String ServerAddress = DBConstant.DB_IP;
    static final int PORT = DBConstant.PORT;

    public MongoHelper(){
    }

    public MongoClient getMongoClient( ){
        MongoClient mongoClient = null;
        try {
            // 连接到 mongodb 服务
            mongoClient = new MongoClient(ServerAddress, PORT);
            System.out.println("Connect to mongodb successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return mongoClient;
    }

    public MongoDatabase getMongoDataBase(MongoClient mongoClient) {
        MongoDatabase mongoDataBase = null;
        try {
            if (mongoClient != null) {
                // 连接到数据库
                mongoDataBase = mongoClient.getDatabase(DBName);
                System.out.println("Connect to DataBase successfully");
            } else {
                throw new RuntimeException("MongoClient不能够为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mongoDataBase;
    }

    public void closeMongoClient(MongoDatabase mongoDataBase,MongoClient mongoClient ) {
        if (mongoDataBase != null) {
            mongoDataBase = null;
        }
        if (mongoClient != null) {
            mongoClient.close();
        }
        System.out.println("CloseMongoClient successfully");
    }
    public static <T> T dbObject2Bean(DBObject dbObject, T bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            return null;
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            String varName = field.getName();
            Object object = dbObject.get(varName);
            if (object != null) {
                BeanUtils.setProperty(bean, varName, object);
            }
        }
        return bean;
    }
}
