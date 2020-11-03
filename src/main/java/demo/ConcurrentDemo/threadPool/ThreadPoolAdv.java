package demo.ConcurrentDemo.threadPool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池适配
 * @author 曾鹏
 */
public class ThreadPoolAdv {

    /**
     * 线程创建工厂
     * 自定义线程池的创建方式,将线程为守护线程
     */
     static class MyThreadFactory implements ThreadFactory{


        private AtomicInteger count=new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            //创建线程，名称规则可以自行定义
            Thread t=new Thread("ZP_"+count.getAndIncrement());

            //将线程设置为守护线程
            t.setDaemon(true);

            System.out.println("create"+t);
            return t;
        }
    }
}
