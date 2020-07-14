package demo.ConcurrentDemo.AQS;

import java.time.temporal.Temporal;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 * 测试SelfLock
 */
public class SelfLockTest {
    public void test() throws InterruptedException {
        final Lock lock=new SelfLock();
        class Worker implements Runnable{

            @Override
            public void run() {
                lock.lock();
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }

        /**
         * 启动四个线程
         */
        for (int i = 0; i < 4; i++) {
            Worker w=new Worker();
            new Thread(w).start();
        }

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println("--------------------------------");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SelfLockTest test=new SelfLockTest();
        test.test();
    }
}
