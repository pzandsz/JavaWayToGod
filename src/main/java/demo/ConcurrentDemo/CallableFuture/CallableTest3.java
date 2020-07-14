package demo.ConcurrentDemo.CallableFuture;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.concurrent.*;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class CallableTest3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> c1=new CallTask(5,15);
        Callable<Integer> c2=new CallTask(6,15);
        Callable<Integer> c3=new CallTask(7,15);
        Callable<Integer> c4=new CallTask(6,15);
        Callable<Integer> c5=new CallTask(7,15);


        //定义容量为2的阻塞队列
        BlockingQueue<Runnable> blockingQueue=new ArrayBlockingQueue<>(1);

        //创建线程池，线程核心数为2，最大数为4，线程空闲后存活时间为1秒
        ThreadPoolExecutor executor=new ThreadPoolExecutor(2, 4,
                1, TimeUnit.SECONDS, blockingQueue,
                new ThreadPoolExecutor.DiscardOldestPolicy());

        Future<Integer> f1 = executor.submit(c1);

        Future<Integer> f2 = executor.submit(c2);
        Future<Integer> f3 = executor.submit(c3);
        Future<Integer> f4 = executor.submit(c4);
        Future<Integer> f5 = executor.submit(c5);


        System.out.println("f1:"+f1.get());
        System.out.println("f2:"+f2.get());
        System.out.println("f3:"+f3.get());
        System.out.println("f4:"+f4.get());
        System.out.println("f5:"+f5.get());

        //关闭线程池
//        executor.shutdown();


    }



    static class CallTask implements Callable<Integer>{


        private int a;
        private int b;
        public CallTask(int a,int b){
            this.a=a;
            this.b=b;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName()+"正在执行");
            //等待10秒
            Thread.sleep(1000);
            return a+b;
        }
    }
}
