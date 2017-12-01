package msg;

import Connection.Sender;
import org.apache.thrift.TBase;

/**
 * Created by ChengCe on 2017/12/1.
 */
public class ThriftMsg implements IMessage ,java.io.Serializable{
    private TBase<?, ?> message;
    @Override
    public int getMessageId() {
        return 0;
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
