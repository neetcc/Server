package Connection;

import Util.IpUtil;
import constant.MsgHandlerLoader;
import io.netty.channel.Channel;
import msg.ThriftMsg;
import org.apache.thrift.TBase;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class ConnectionObject implements Sender {

    protected Channel channel;
    protected String clientId;

    public boolean isConnected() {
        return this.channel == null?false:this.channel.isActive();
    }
    
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    
    public String getIp() {
        return IpUtil.getChannelIp(channel);
    }
    
    public void close() {
        if (this.channel == null) {
            return;
        }
        this.channel.close();
    }
    
    public Object getId() {
        return clientId;
    }

    public void sendMessage(TBase<?, ?> message) {
        if (this.isConnected()) {
            ThriftMsg packet = new ThriftMsg(message);
            packet.setMessageId(MsgHandlerLoader.getMsgId(message.getClass()));
            this.channel.writeAndFlush(packet);
        }
    }
}
