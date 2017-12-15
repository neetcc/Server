package Test;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChengCe on 2017/12/8.
 */
public abstract class TestClass<T> {
    private Class entityclass;
    public static Map<Class,Integer> classmap = new HashMap<>();
    
    public Class getMyClass(){
        Class<T>  entityclass = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityclass;
    }
    
    public TestClass(){
        entityclass = getMyClass();
        if(classmap.containsKey(entityclass)){
            classmap.put(entityclass,classmap.get(entityclass)+1);
        }else{
            classmap.put(entityclass,1);
        }
    }
    public static void main(String[] args){
        Son te = new Son();
        Son1 te1 = new Son1();
        Son te2 = new Son();
        for(Map.Entry<Class, Integer> t : TestClass.classmap.entrySet()){
            System.out.print(t.getKey());
            System.out.println(" num: "+t.getValue());
        }

    }
}
class Son extends TestClass<Integer>{
    
}
class Son1 extends TestClass<Long>{
    
}