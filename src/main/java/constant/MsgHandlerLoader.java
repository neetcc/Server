package constant;

import msg.CS.CSPingMsg;
import msg.CSPingHandler;
import msg.IMessageHandler;
import org.apache.thrift.TBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class MsgHandlerLoader {
    protected  static Map<Class<? extends TBase<?, ?>>,Integer > messageMap = new HashMap<>();
    protected static Map<Integer , IMessageHandler> handlerMap = new HashMap<>();
    public static void loadHandler(){
        messageMap.put( CSPingMsg.class,MsgConstant.CS_PING_MSG);
        handlerMap.put(MsgConstant.CS_PING_MSG,new CSPingHandler());
    }
    
    public static int getMsgId(Class<? extends TBase> messageClass){
        return messageMap.get(messageClass);
    }
    
    public static IMessageHandler getMsgHandler(int msgId){
        return handlerMap.get(msgId);
    }
}
