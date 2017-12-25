package client;

import java.net.InetSocketAddress;

/**
 * Created by ChengCe on 2017/12/18.
 */
public class Address {
    private int port;
    private String ip;

    public Address(){
        
    }
    public Address(InetSocketAddress address){
        this.port = address.getPort();
        this.ip = address.getHostString();
    }
    public Address(String ip, int port){
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
        if(remoteAddr instanceof Address){
            Address ra = (Address) remoteAddr;
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
        Map<Address , Address > map = new HashedMap();
        map.put(new Address("127.0.0.1",1),new Address("127.0.0.1",1));
        if(map.containsKey(new Address("127.0.0.1",1))){
            System.out.println(1111);
        }
    }*/
}
