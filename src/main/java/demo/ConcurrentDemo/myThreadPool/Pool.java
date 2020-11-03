package demo.ConcurrentDemo.myThreadPool;

/**
 * 定义一个池接口
 *
 * @author 曾鹏
 */
public interface Pool {
    /**
     * 或者执行器,线程的具体执行是交给执行器来进行的
     * @return
     */
    Executor getExecutor() throws InterruptedException;

    /**
     * 销毁
     */
    void  destroy();
}
