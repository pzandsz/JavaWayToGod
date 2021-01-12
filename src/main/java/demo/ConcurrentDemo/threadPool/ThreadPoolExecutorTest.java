package demo.ConcurrentDemo.threadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * JUC线程池测试
 * @author zengpeng
 */
public class ThreadPoolExecutorTest {

    private static ThreadPoolExecutor getThreadPoolExecutor(){
        int coreThreads = 5;
        int maxThreads = 10;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executors = new ThreadPoolExecutor(coreThreads,maxThreads,keepAliveTime,unit,blockingQueue);
        return executors;
    }


    static class WorkTask implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }


    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        for(int i=0 ; i<10 ; i++){
            threadPoolExecutor.execute(new WorkTask());
        }

    }

}
