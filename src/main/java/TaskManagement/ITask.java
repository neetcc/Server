package TaskManagement;

/**
 * Created by ChengCe on 2017/12/5.
 */
public interface ITask {
    TaskState before();
    TaskState running();
    TaskState after();
}
