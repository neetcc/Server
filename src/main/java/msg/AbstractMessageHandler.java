package msg;

import Connection.Sender;
import org.apache.thrift.TBase;

/**
 * Created by ChengCe on 2017/12/1.
 */
public abstract class AbstractMessageHandler<M extends TBase, S extends Sender> implements IMessageHandler{
   @Override
    public void execute(IMessage message){
     Sender sender =  message.getSender();
     ThriftMsg msg = (ThriftMsg)message;
     doExecute((M) msg.getMessage(), (S)sender);
   }
   
   protected abstract void doExecute(M msg, S sender);
}
