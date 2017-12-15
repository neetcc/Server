package Test;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class Test {
   public static  ArrayList<Integer> l = new ArrayList<>();
    public static void main(String[] args){

        
//      
//        add(1);
//        add(3);
//        add(2);
//        add(100);
//        add(33);
//        System.out.println(l);
    }
    
    public static void add (int a){
        int pos = l.size()-1;
        while(pos>=0){
            if(a>l.get(pos)){
                break;
            }
            pos--;
        }
        l.add(++pos,a);
    }
}
