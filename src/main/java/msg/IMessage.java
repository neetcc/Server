package msg;

import Connection.Sender;

/**
 * Created by ChengCe on 2017/12/1.
 */
public interface IMessage {
    int getMessageId();

    Sender getSender();
}
