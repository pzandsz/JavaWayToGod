package demo.ConcurrentDemo.CommenClass;

import algorithm.wangyi.Ta;

import java.sql.SQLOutput;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 类说明:使用CountDownLatch模拟并发
 *
 * @author 曾鹏
 */
public class BingFaTest {

//    private CountDownLatch latch=new CountDownLatch(10);
//    /**
//     * 用于模拟信号枪
//     */
//    private CountDownLatch sign=new CountDownLatch(1);





    static class Task implements Runnable{
        private CountDownLatch latch;

        public Task(CountDownLatch latch){
            this.latch=latch;
        }

        @Override
        public void run() {
            latch.countDown();
            System.out.println(latch.getCount());
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程"+ Thread.currentThread()+"正在运行...");

        }
    }

    public static void main(String[] args) {

        ThreadPoolExecutor executor= (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        CountDownLatch latch=new CountDownLatch(10);
        for (int i=0;i<10;i++){
            executor.submit(new Task(latch));
        }
    }
}
