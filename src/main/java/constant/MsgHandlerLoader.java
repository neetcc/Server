package constant;

import Handler.CS.CSLinkRequestMsgHandler;
import Handler.SC.SCLinkRequestMsgHandler;
import Handler.SC.SCPingHandler;
import msg.CS.CSLinkRequestMsg;
import msg.CS.CSPingMsg;
import Handler.CS.CSPingHandler;
import msg.IMessageHandler;
import msg.SC.SCLinkRequestMsg;
import msg.SC.SCPingMsg;
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
        messageMap.put(SCPingMsg.class, MsgConstant.SC_PING_MSG);
        messageMap.put(CSLinkRequestMsg.class,MsgConstant.CS_LINKREQUEST_MSG);
        messageMap.put(SCLinkRequestMsg.class,MsgConstant.SC_LINK_REQUEST_MSG);
        handlerMap.put(MsgConstant.CS_PING_MSG,new CSPingHandler());
        handlerMap.put(MsgConstant.SC_PING_MSG,new SCPingHandler());
        handlerMap.put(MsgConstant.CS_LINKREQUEST_MSG,new CSLinkRequestMsgHandler());
        handlerMap.put(MsgConstant.SC_LINK_REQUEST_MSG, new SCLinkRequestMsgHandler());
        
    }
    
    public static int getMsgId(Class<? extends TBase> messageClass){
          return messageMap.get(messageClass);
    }
    
    public static IMessageHandler getMsgHandler(int msgId){
        return handlerMap.get(msgId);
    }
}
