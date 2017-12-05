package TaskManagement;

/**
 * Created by ChengCe on 2017/12/5.
 */
public abstract class AbstractSimpleTask implements ITask {
    @Override
    public TaskState before(){
        return TaskState.Running;
    }
    @Override
    public TaskState running(){
        execute();
        return TaskState.After;
    }
    
    @Override
    public TaskState after(){
        callback();
        return TaskState.Done;
    }
    protected abstract void execute();
    protected abstract void callback();
}
