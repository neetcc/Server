package constant;

import Handler.CC.CCChatMsgHandler;
import Handler.CC.CCPingMsgHandler;
import Handler.CS.CSLinkRequestMsgHandler;
import Handler.CS.CSPortReportMsgHandler;
import Handler.SC.SCLinkRequestMsgHandler;
import Handler.SC.SCPingHandler;
import msg.CC.CCChatMsg;
import msg.CC.CCPingMsg;
import msg.CS.CSLinkRequestMsg;
import msg.CS.CSPingMsg;
import Handler.CS.CSPingHandler;
import msg.CS.CSPortReportMsg;
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
        messageMap.put(CSLinkRequestMsg.class,MsgConstant.CS_LINK_REQUEST_MSG);
        messageMap.put(SCLinkRequestMsg.class,MsgConstant.SC_LINK_REQUEST_MSG);
        messageMap.put(CSPortReportMsg.class, MsgConstant.CS_PORT_REPORT_MSG);
        messageMap.put(CCChatMsg.class,MsgConstant.CC_CHAT_MSG);
        messageMap.put(CCPingMsg.class,MsgConstant.CC_PING_MSG);
        
        
        handlerMap.put(MsgConstant.CS_PING_MSG,new CSPingHandler());
        handlerMap.put(MsgConstant.SC_PING_MSG,new SCPingHandler());
        handlerMap.put(MsgConstant.CS_LINK_REQUEST_MSG,new CSLinkRequestMsgHandler());
        handlerMap.put(MsgConstant.SC_LINK_REQUEST_MSG, new SCLinkRequestMsgHandler());
        handlerMap.put(MsgConstant.CS_PORT_REPORT_MSG, new CSPortReportMsgHandler());
        handlerMap.put(MsgConstant.CC_CHAT_MSG, new CCChatMsgHandler());
        handlerMap.put(MsgConstant.CC_PING_MSG, new CCPingMsgHandler());
    }
    
    public static int getMsgId(Class<? extends TBase> messageClass){
          return messageMap.get(messageClass);
    }
    
    public static IMessageHandler getMsgHandler(int msgId){
        return handlerMap.get(msgId);
    }
}
