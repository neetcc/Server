package client;

import java.net.InetSocketAddress;

/**
 * Created by ChengCe on 2017/12/18.
 */
public class MAddress {
    private int port;
    private String ip;

    public MAddress(){
        
    }
    public MAddress(InetSocketAddress address){
        this.port = address.getPort();
        this.ip = address.getHostString();
    }
    public MAddress(String ip, int port){
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
        if(remoteAddr instanceof MAddress){
            MAddress ra = (MAddress) remoteAddr;
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
        Map<MAddress , MAddress > map = new HashedMap();
        map.put(new MAddress("127.0.0.1",1),new MAddress("127.0.0.1",1));
        if(map.containsKey(new MAddress("127.0.0.1",1))){
            System.out.println(1111);
        }
    }*/
}
