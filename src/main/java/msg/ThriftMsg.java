package msg;

import Connection.Sender;
import org.apache.thrift.TBase;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class ThriftMsg implements IMessage ,java.io.Serializable{
    private TBase<?, ?> message;
    private int messageId;
    private Sender sender;
    @Override
    public int getMessageId() {
        return messageId;
    }
    
    public void setMessageId(int messageId){
        this.messageId = messageId;
    }
    public void setSender(Sender sender){
        this.sender = sender;
    }
    @Override
    public Sender getSender() {
        return null;
    }
    public void setMessage(TBase<?, ?> message) {
        this.message = message;
    }
    public ThriftMsg(TBase<?, ?> message){
        this.message = message;
    }

    public TBase<?, ?> getMessage() {
        return message;
    }
}
