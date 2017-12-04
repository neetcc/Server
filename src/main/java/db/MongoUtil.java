package db;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by ChengCe on 2017/12/4.
 */
public class MongoUtil {
    public static MongoUtil mongoUtil = null;
    public static MongoClient mongoClient = null;
    public static MongoDatabase mongoDataBase = null;

    private MongoUtil() {
    }

    public static MongoUtil getInstance() {
        if (mongoUtil == null) {
            mongoUtil = new MongoUtil();
        }
        return mongoUtil;
    }
    
    /**
     * 将实体类的obj的字段信息和内容动态放到mapParams里面 
     *
     * @param mapParams
     * @param obj
     * @param method
     */
    public void dymParms(Object mapParams, Object obj, String method) {
        try {
            if (obj != null) {
                Field[] fields = obj.getClass().getDeclaredFields();
                Class<?>[] arrClazz = new Class[2];
                arrClazz[0] = String.class;
                arrClazz[1] = Object.class;
                Method m = mapParams.getClass().getDeclaredMethod(method,
                        arrClazz);
                m.setAccessible(true);
                if (fields != null) {
                    for (Field f : fields) {
                        f.setAccessible(true);
                        Object value = f.get(obj);
                        if (null!=value) {
                            Class<?> clazz = value.getClass();
                            Object[] strs = new Object[2];
                            if (clazz == String.class) {
                                if ( !"".equals(String.valueOf(value))) {
                                    strs[0] = f.getName();
                                    if(f.getName() == "id"){
                                        strs[0] = "_id";
                                    }
                                    strs[1] = value;
                                    m.invoke(mapParams, strs);
                                }
                            } else {
                                strs[0] = f.getName();
                                if(f.getName() == "id"){
                                    strs[0] = "_id";
                                }
                                strs[1] = value;
                                m.invoke(mapParams, strs);
                            }
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public static <T> DBObject bean2DBObject(T bean) throws IllegalArgumentException,
            IllegalAccessException {
        if (bean == null) {
            return null;
        }
        DBObject dbObject = new BasicDBObject();
        // 获取对象对应类中的所有属性域  
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 获取属性名  
            String varName = field.getName();
            // 修改访问控制权限  
            boolean accessFlag = field.isAccessible();
            if (!accessFlag) {
                field.setAccessible(true);
            }
            Object param = field.get(bean);
            if (param == null) {
                continue;
            } else if (param instanceof Integer) {//判断变量的类型  
                int value = ((Integer) param).intValue();
                dbObject.put(varName, value);
            } else if (param instanceof String) {
                String value = (String) param;
                dbObject.put(varName, value);
            } else if (param instanceof Double) {
                double value = ((Double) param).doubleValue();
                dbObject.put(varName, value);
            } else if (param instanceof Float) {
                float value = ((Float) param).floatValue();
                dbObject.put(varName, value);
            } else if (param instanceof Long) {
                long value = ((Long) param).longValue();
                dbObject.put(varName, value);
            } else if (param instanceof Boolean) {
                boolean value = ((Boolean) param).booleanValue();
                dbObject.put(varName, value);
            } else if (param instanceof Date) {
                Date value = (Date) param;
                dbObject.put(varName, value);
            }
            // 恢复访问控制权限  
            field.setAccessible(accessFlag);
        }
        return dbObject;
    }
}
