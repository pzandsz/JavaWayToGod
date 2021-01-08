package demo.ConcurrentDemo.AQS;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedTest {

    private Object lock = new Object();

    class Worker implements Runnable{

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+"：" + Thread.currentThread().getName()+": 尝试获取锁！");

            synchronized (SynchronizedTest.class) {
                System.out.println(System.currentTimeMillis()+"：" + Thread.currentThread().getName()+": 成功加锁！");

                try {
                    Thread.sleep(10000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void test() throws InterruptedException {


        /**
         * 启动四个线程
         */
        for (int i = 0; i < 4; i++) {

            SynchronizedTest.Worker w = new SynchronizedTest.Worker();
            new Thread(w).start();
        }

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
//            System.out.println(i + ":--------------------------------");
        }
    }



    public static void main(String[] args) throws InterruptedException {
        SynchronizedTest test=new SynchronizedTest();
        test.test();
    }
}
