package demo.ConcurrentDemo.CallableFuture;

import java.util.concurrent.*;

/**
 * 测试：实现带参数返回的线程，基于Callable,FutureTask组合实现
 * @author 曾鹏
 *
 * 使用Callable+Future获取执行结果
 */
public class CallableTest2 {

    /**
     * 定义一个任务类，用于计算指定数组的累加和
     * 该类通过实现Callable接口实现线程计算结果的返回
     * 返回Integer类型的数据
     */
    static class CountTask implements Callable<Integer>{

        private int[] list;

        public CountTask(int[] list){
            this.list=list;
        }

        @Override
        public Integer call() {
            System.out.println("子线程正在计算...");
            int count=0;
            for (int i=0;i<list.length;i++){
                count+=list[i];
            }
            return new Integer(count);
        }
    }

    public static void main(String[] args) {
        int[] list={1,2,3,4,5,6,7,8,9};

        /**
         * newCachedThreadPool:创建一个可缓存线程池，如果线程池长度超出处理需要就会灵活回收空闲线程，
         * 若无可回收，则新建线程
         *
         * newCachedThreadPool中默认创建一个缓存时间为60秒的线程池,除此之外，CachedThreadPool还提供一个
         * SynchronousQueue队列，用于处理等待中的线程
         */
        ExecutorService executor = Executors.newCachedThreadPool();

        //创建计算任务
        CallableTest1.CountTask task = new CallableTest1.CountTask(list);

        /**
         * FutureTask<>类提供了两个构造方法:
         *      1.public FutureTask(Callable<V> callable)
         *      2.public FutureTask(Runnable runnable, V result)
         * 第一种构造方法传入的参数是实现了Callable接口的对象，下列代码使用的就是这种方式
         * 第二种构造方法传入的参数是实现了Runnable接口的对象以及一个用于存储结果的泛型对象,
         * 其实第二种方法在内部还是创建了一个Callable对象并赋值给callable属性
         *
         * 两种构造方法均执行了:this.state = NEW;其中state使用volatile,使其有了可见性.
         * 除了NEW（新建）之外,还有:
         * COMPLETING(完成),NORMAL（正常运行）,EXCEPTIONAL(异常退出),
         * CANCELLED(任务取消),INTERRUPTING(线程中断中),INTERRUPTED(线程已中断)这些状态，
         * 这七种状态可能存在的转换有:
         * NEW -> COMPLETING -> NORMAL
         * NEW -> COMPLETING -> EXCEPTIONAL
         * NEW -> CANCELLED
         * NEW -> INTERRUPTING -> INTERRUPTED
         *
         */
        FutureTask<Integer> futureTask=new FutureTask<>(task);
        /**
         * 该行代码是将任务提交到线程池中，线程池会返回一个结果对象Future
         * ExecutorService中的submit(Callable<T> task)返回的是一个Future<T>对象
         * Future对象可以通过get()方法获得线程任务的返回值
         * 同时也提供get(long timeout,TimeUnit unit):表示在指定时间内没有获得返回值，就直接返回null
         */
        executor.submit(futureTask);

        /**
         * shutdown():停止接收新任务，但是原任务继续执行
         * shutdownNow():停止接收新任务，同时原任务停止执行
         */
        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("主线程在执行任务");

        try {
            System.out.println("task运行结果"+futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }
}
