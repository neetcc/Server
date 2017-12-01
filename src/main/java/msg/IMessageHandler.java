package msg;

/**
 * Created by ChengCe on 2017/12/1.
 */
public interface IMessageHandler {
    int getMessageIds();

    void execute(IMessage message);
}
