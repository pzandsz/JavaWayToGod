package demo.ConcurrentDemo.CommenClass;

import com.sun.org.apache.xml.internal.security.Init;

import java.util.concurrent.CountDownLatch;

/**
 * 测试 CountDownLatch： CountDownLatch位于java.util.concurrent包
 * CountDownLatch使用场景：当有一个任务A，它要等待其他四个线程执行完毕后才能执行
 * （例如Spring容器启动需要准备线程执行完后才能执行），
 * 此时就可以使用CountDownLatch实现
 * @author 曾鹏
 */
public class CountDownLatchTest {

    /**
     * 模拟初始化任务
     */
    static class InitTask implements Runnable{

        private CountDownLatch latch;

        public InitTask(CountDownLatch latch){
            this.latch=latch;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"线程初始化开始执行....");
            try {
                //模拟线程执行过程
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+"线程执行完毕!");

            /**
             * CountDownLatch重要方法之一:将count值减1
             */
            latch.countDown();

        }
    }

    /**
     * 模拟非初始化线程
     */
    static class UnInitTask implements Runnable{
        private CountDownLatch latch;

        public UnInitTask(CountDownLatch latch){
            this.latch=latch;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"等待初始化线程执行完毕!");
            try {
                /**
                 * CountDownLatch的重要方法之一：调用该方法的线程会被挂起，直到count=0才会继续执行
                 * await(long timeout,TimeUnit unit):和await()类似，只不过等待一定的时间后count值
                 * 还没变为0的话就会继续执行。
                 */
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+"非初始化线程开始执行....");
            try {
                //模拟线程执行过程
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"非初始化线程执行完毕!");
        }
    }


    public static void main(String[] args) {
        /**
         * 构造方法中的参数:(int count),count表示计数器
         */
        CountDownLatch latch=new CountDownLatch(5);

        //模拟初始化
        long len=latch.getCount();
        for(int i=0;i<len;i++){
            new Thread(new InitTask(latch)).start();
        }

        //执行线程，需要等待初始化完成才能执行
        new Thread(new UnInitTask(latch)).start();

    }
}
