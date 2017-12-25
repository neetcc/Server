package client;

import Connection.ConnectionObject;
import msg.CC.CCChatMsg;

/**
 * Created by ChengCe on 2017/12/18.
 */
public class ChatSer {
    private Client me;
    
    public void chatWithChar(long id , String content){
        MAddress other = me.getOtherAddr(id);
        if(other == null){
            return;
        }
        ConnectionObject co = me.getOtherCO(other);
        if(co == null){
            return;
        }
        CCChatMsg msg = new CCChatMsg(id, content);
        co.sendMessage(msg);
    }
    public ChatSer(Client client){
        this.me = client;
    }
    public Client getMe() {
        return me;
    }

    public void setMe(Client me) {
        this.me = me;
    }
}
