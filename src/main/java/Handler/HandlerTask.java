package Handler;

import Connection.Sender;

/**
 * Created by ChengCe on 2017/12/5.
 */
public class HandlerTask extends AbstractHandlerTask {
    @Override
    protected void onGenerateUser(long id, Sender sender) {
        System.out.println("call back");
    }
}
