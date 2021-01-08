package demo.ConcurrentDemo.AQS;

import java.time.temporal.Temporal;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 * 测试SelfLock
 */
public class SelfLockTest {
    final Lock lock=new SelfLock();

    class Worker implements Runnable{

        @Override
        public void run() {
            lock.lock();
            try {
                Thread.sleep(10000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
    public void test() throws InterruptedException {


        /**
         * 启动四个线程
         */
        for (int i = 0; i < 100; i++) {
            Worker w=new Worker();
            new Thread(w).start();
        }

//        for (int i = 0; i < 10; i++) {
//            Thread.sleep(1000);
////            System.out.println(i + ":--------------------------------");
//        }
    }

    public static void main(String[] args) throws InterruptedException {
        SelfLockTest test=new SelfLockTest();
        test.test();
    }
}
