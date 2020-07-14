package demo.ConcurrentDemo.myThreadPool;

/**
 * 定义一个池接口
 * @author 曾鹏
 */
public interface Pool {
    /**
     * 或者执行器
     * @return
     */
    Executor getExecutor() throws InterruptedException;

    /**
     * 销毁
     */
    void  destroy();
}
