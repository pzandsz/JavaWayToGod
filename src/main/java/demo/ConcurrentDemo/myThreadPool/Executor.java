package demo.ConcurrentDemo.myThreadPool;

/**
 * 执行器接口
 * @author 曾鹏
 */
public interface Executor {

    /**
     * 设置任务
     * @param task
     */
    void  setTask(Task task);

    /**
     * 获得任务
     * @return
     */
    Task getTask();

    /**
     * 开始执行任务
     */
    void  startTask();
}
