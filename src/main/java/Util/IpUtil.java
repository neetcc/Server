package Util;

import constant.ProtocolConstant;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class IpUtil {
    public static String getChannelIp(Channel channel){
        String ip = "255.255.255.255";
        try{
            if(channel == null){
                return ip;
            }
            Attribute<String> ipAttr = channel.attr(ProtocolConstant.KEY_IPADDR);
            if(ipAttr.get() != null){
                return ipAttr.get();
            }
            InetSocketAddress addr = (InetSocketAddress)channel.remoteAddress();
            if(addr == null){
                addr = (InetSocketAddress) channel.localAddress();
            }
            InetAddress ia = addr.getAddress();
            if(ia instanceof Inet4Address){
                ip = ((Inet4Address) ia).getHostAddress();
            }else if(ia instanceof Inet6Address){
                ip = ((Inet6Address) ia).getHostAddress();
            }
            ipAttr.set(ip);
        } catch(Exception e){

        }
        return ip;
    }
}
