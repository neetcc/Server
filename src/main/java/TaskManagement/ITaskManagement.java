package TaskManagement;


/**
 * Created by ChengCe on 2017/12/5.
 */
public interface ITaskManagement {
    <T> T createITask(Class<? extends ITask> task, Object...args);
    
    void executeITask(ITask itask);
    void executeITask(ITask itask, TaskState taskState);
    void executeITask(Class<? extends ITask> task,Object...args);
}
