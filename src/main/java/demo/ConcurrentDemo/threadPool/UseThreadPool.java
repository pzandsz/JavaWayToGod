package demo.ConcurrentDemo.threadPool;

import demo.ConcurrentDemo.myThreadPool.Executor;
import demo.ConcurrentDemo.myThreadPool.MyThreadPool;

import java.util.concurrent.*;

/**
 * 使用线程池
 */
public class UseThreadPool {
    public static void main(String[] args) {

        /**
         * 创建ThreadPoolExecutor时，各个参数的含义如下
         * corePoolSize:核心线程数,核心线程全部被分配完了之后，接下来进入的线程将会进入阻塞队列
         * maximumPoolSize:最大线程数，当阻塞队列也满了的时候，线程数量小于maximumPoolSize的话，
         *      就会再创建一个线程资源。
         * keepAliveTime:和TimeUnit时间单位联合使用，表示线程空闲后的存活时间
         *
         * new ArrayBlockingQueue<Runnable>(10):用于存储等待线程的组色队列，容量在这里设定为10
         *
         * new ThreadPoolAdv.MyThreadFactory(),自定义创建的线程池的方式
         *
         * new ThreadPoolExecutor.DiscardOldestPolicy()：当线程数量达到最大线程数maximumPoolSize且
         * 阻塞队列也满了，此时如果还有新的任务请求线程资源，将会使用DiscardOldestPolicy拒绝策略，将
         * 阻塞队列中等待最久的线程清除出队列
         */
        ExecutorService pool=new ThreadPoolExecutor(2,4,3,
                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10),
//                new ThreadPoolAdv.MyThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()){

            //切面方法，执行线程之前会执行
            @Override
            protected void beforeExecute(Thread t,Runnable r){
                System.out.println("Ready Execute-->"+((Work)r).getName());
            }

            //切面方法，执行线程之后会执行
            @Override
            protected void afterExecute(Runnable r, Throwable t){
                System.out.println("Complete Execute-->"+((Work)r).getName());
            }
        };

        /**
         * 不推荐使用这些方法的原因是因为这些方法的阻塞队列长度约为20多个亿
         * ExecutorService pool=
         *  1.Executors.newCachedThreadPool():最大线程数约20多个亿:一有任务过来就创建一个线程
         *  2.Executors.newFixedThreadPool():创建固定大小的线程池
         *  3.Executors.newSingleThreadExecutor():单线程线程池,核心线程数和最大线程数都是1
         *  4.Executors.newWorkStealingPool():支持工作密取,通过fork/join实现
         *
         *  5.Executors.newScheduledThreadPool():支持定时执行的线程池（Runnable和Callable）
         *      支持线程仅执行一次
         *      固定延迟时间间隔执行的任务
         *      固定时间间隔执行的任务
         *      固定延时间隔执行的任务
         *      (注意延时时间间隔,时间间隔和延时间隔的区别)
         *
         *      在这个线程池中出现的异常，如果不使用try()catch,异常会被Scheduled吃掉
         */

        for (int i = 0; i < 6; i++) {
            Work work=new Work("work"+i);
            pool.execute(work);
        }

        pool.shutdown();
    }

    static class Work implements Runnable{

        private String name;

        public String getName() {
            return name;
        }

        public Work(String name){
            this.name=name;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"线程正在处理");
        }
    }

}
