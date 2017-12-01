package Connection;

import io.netty.channel.Channel;

/**
 * Created by ChengCe on 2017/12/1.
 */
public interface Sender {
    void setChannel(Channel channel);

    String getIp();

    void close();

    Object getId();
}
