package client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


/**
 * Created by ChengCe on 2017/12/1.
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /*
         * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
         * 
         * 解码和编码 我将会在下一张为大家详细的讲解。再次暂时不做详细的描述
         * 
         * */
        pipeline.addLast("decode",new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
        pipeline.addLast("Encode", new ObjectEncoder());

            
        // 客户端的逻辑
        pipeline.addLast("handler", new ClientHandler());
    }
}