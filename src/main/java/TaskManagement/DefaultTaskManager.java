package TaskManagement;


import msg.IMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ChengCe on 2017/12/5.
 */
public class DefaultTaskManager implements ITaskManagement {
    private  final ExecutorService executor;
    private  final IMessageQueue messageQueue;
    private Map<Class<?>, String> taskNameMap = new HashMap<>( );

    public DefaultTaskManager(){
        executor = Executors.newFixedThreadPool(TaskConstant.TASK_NUM);
        messageQueue = new IMessageQueue(TaskConstant.MSG_NUM);
    }
    
    @Override
    public <T> T createITask(Class<? extends ITask> task, Object... args) {
        return null;
    }

    @Override
    public void executeITask(ITask itask) {
        executeITask(itask, TaskState.Before);
    }

    @Override
    public void executeITask(ITask itask, TaskState taskState) {
        if(taskState == null){
            taskState = TaskState.Done;
        }
        switch (taskState){
            case Before:{
               TaskState state = itask.before();
               if(state == TaskState.Before){
                   System.out.println("task state loop with "+ itask.getClass().getName());
                   break;
               }
               executeITask(itask,state);}
                break;
            case Running:
                executor.execute(()->{
                    TaskState state = itask.running();
                    if(state == TaskState.Running){
                        System.out.println("task state loop with "+ itask.getClass().getName());
                        return;
                    }
                    executeITask(itask,state);
                });
                break;
            case After:{
                TaskState state = itask.after();
                if(state == TaskState.After){
                    System.out.println("task state loop with "+ itask.getClass().getName());
                    break;
                }
                executeITask(itask,state);}
                break;
            default:
                break;
        }
    }

    @Override
    public void executeITask(Class<? extends ITask> task, Object... args) {

    }
}
