package demo.ConcurrentDemo.CallableFuture;

import java.util.concurrent.*;


/**
 * 测试：实现带参数返回的线程，基于Callable,Future组合实现
 * @author 曾鹏
 *
 * 使用Callable+Future获取执行结果
 */
public class CallableTest1 {

    /**
     * 定义一个任务类，用于计算指定数组的累加和
     * 该类通过实现Callable接口实现线程计算结果的返回
     * 返回Integer类型的数据
     */
    static class CountTask implements Callable<Integer> {

        private int[] list;

        public CountTask(int[] list){
            this.list = list;
        }

        @Override
        public Integer call() {
            System.out.println("子线程正在计算...");
            int count = 0;
            for (int i = 0; i < list.length ; i++){
                count += list[i];
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
        CountTask task = new CountTask(list);

        /**
         * 该行代码是将任务提交到线程池中，线程池会返回一个结果对象Future
         * ExecutorService中的submit(Callable<T> task)返回的是一个Future<T>对象
         * Future对象可以通过get()方法获得线程任务的返回值,这个方法会产生阻塞，会一直等到任务执行完毕才返回；
         * 同时也提供get(long timeout,TimeUnit unit):表示在指定时间内没有获得返回值，就直接返回null
         */
        Future<Integer> result = executor.submit(task);

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
            System.out.println("task运行结果"+result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }
}
