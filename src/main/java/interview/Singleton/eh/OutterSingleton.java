package interview.Singleton.eh;

import demo.ConcurrentDemo.myThreadPool.Executor;

import java.util.concurrent.*;

/**
 * 类说明:静态内部类形式的懒汉单例加载,线程安全
 *
 * 只有当内部类被加载时，才会执行内部类的构造方法，从而获得单例对象
 *
 * @author 曾鹏
 */
public class OutterSingleton {


    private OutterSingleton(){

    }

    /**
     * 内部类
     */
    private static class InnerSingleton{
        private static OutterSingleton singletonInstance=new OutterSingleton();

    }

    public static OutterSingleton getSingleton(){
        return InnerSingleton.singletonInstance;
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //定义一个任务
        Callable<OutterSingleton> c1=new Callable<OutterSingleton>() {
            @Override
            public OutterSingleton call() throws Exception {
                Thread.sleep(1000);
                OutterSingleton singleton = OutterSingleton.getSingleton();
                return singleton;
            }
        };



        //创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<OutterSingleton> f1 = pool.submit(c1);
        Future<OutterSingleton> f2 = pool.submit(c1);


        OutterSingleton singleton1 = f1.get();
        OutterSingleton singleton2 = f2.get();

        System.out.println(singleton1==singleton2);

        //关闭线程池
        pool.shutdown();


    }
}
