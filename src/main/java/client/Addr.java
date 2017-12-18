package client;

import org.apache.commons.collections.map.HashedMap;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Created by ChengCe on 2017/12/18.
 */
public class Addr {
    private int port;
    private String ip;

    public Addr(){
        
    }
    public Addr(InetSocketAddress address){
        this.port = address.getPort();
        this.ip = address.getHostString();
    }
    public Addr(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    @Override
    public boolean equals(Object remoteAddr){
        if(remoteAddr instanceof Addr){
            Addr ra = (Addr) remoteAddr;
            return this.ip.equals(ra.ip) && this.port == ra.port ? true : false;
        }else{
            return false;
        }
    }
    @Override
    public int hashCode(){
        return  this.ip.hashCode()+29*port;
    }
/*    public static void main(String[] argv){
        Map<Addr , Addr > map = new HashedMap();
        map.put(new Addr("127.0.0.1",1),new Addr("127.0.0.1",1));
        if(map.containsKey(new Addr("127.0.0.1",1))){
            System.out.println(1111);
        }
    }*/
}
