package demo.ConcurrentDemo.AQS;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1610091005190：Thread-0: 尝试获取锁！
 * 1610091005190：Thread-2: 尝试获取锁！
 * 1610091005190：Thread-1: 尝试获取锁！
 * 1610091005191：Thread-3: 尝试获取锁！
 * 1610091005191：Thread-0: 成功加锁！
 * 1610091005191：Thread-2: 成功加锁！
 * 1610091005191：Thread-3: 成功加锁！
 * 1610091005191：Thread-1: 成功加锁！
 * 1610091015196：Thread-1: 尝试解锁！
 * 1610091015196：Thread-2: 尝试解锁！
 * 1610091015196：Thread-2: 成功解锁！
 * 1610091015196：Thread-3: 尝试解锁！
 * 1610091015196：Thread-0: 尝试解锁！
 * 1610091015196：Thread-1: 成功解锁！
 * 1610091015197：Thread-0: 成功解锁！
 * 1610091015197：Thread-3: 成功解锁！
 */
public class ReentrantLockTest {
    final Lock lock = new ReentrantLock(true);

    class Worker implements Runnable{

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+"：" + Thread.currentThread().getName()+": 尝试获取锁！");
            lock.lock();
            try {
                System.out.println(System.currentTimeMillis()+"：" + Thread.currentThread().getName()+": 成功加锁！");
                Thread.sleep(10000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println(System.currentTimeMillis()+"：" + Thread.currentThread().getName()+": 尝试解锁！");
                lock.unlock();
                System.out.println(System.currentTimeMillis()+"：" + Thread.currentThread().getName()+": 成功解锁！");

            }
        }
    }
    public void test() throws InterruptedException {


        /**
         * 启动四个线程
         */
        for (int i = 0; i < 4; i++) {

            ReentrantLockTest.Worker w = new ReentrantLockTest.Worker();
            new Thread(w).start();
        }

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
//            System.out.println(i + ":--------------------------------");
        }
    }



    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest test=new ReentrantLockTest();
        test.test();
    }
}


