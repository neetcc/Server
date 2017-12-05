package TaskManagement;

import msg.IMessage;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ChengCe on 2017/12/5.
 */
public class IMessageQueue {
    private final Queue<IMessage> messages;
    private final int maxNum;
    public IMessageQueue(int maxNum){
        this.maxNum = maxNum;
        this.messages = new LinkedBlockingQueue<>(maxNum);
    }
    public boolean put(IMessage message){
       return this.messages.offer(message); // false is possible
    }
    
    public IMessage get(){
        return this.messages.poll();// null is possible
    }
    
    public IMessage[] getAll(){
        return this.messages.toArray(new IMessage[messages.size()]);
    }
    
    public int size(){
        return messages.size();
    }
}
