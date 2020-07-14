package demo.ConcurrentDemo.myThreadPool;

/**
 * 任务接口
 * @author 曾鹏
 */
public interface Task {

    /**
     * 执行方法
     */
    void  execute() throws InterruptedException;

    /**
     * 获得结果
     * @return
     */
    byte [] getResult();
}
