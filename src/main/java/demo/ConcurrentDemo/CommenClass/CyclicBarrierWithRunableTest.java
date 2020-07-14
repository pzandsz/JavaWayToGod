package demo.ConcurrentDemo.CommenClass;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 测试带有参数Runable的CyclicBarrier类,实现都到达屏障后执行的操作
 * 当N个线程都到达barrier状态后，会从N个线程中选择一个线程去执行Runnable。
 * @author 曾鹏
 */
public class CyclicBarrierWithRunableTest {

    static class UpdateTask implements Runnable{

        private CyclicBarrier barrier;

        public UpdateTask(CyclicBarrier barrier){
            this.barrier=barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+"正在更新");
                //模拟更新操作
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+"更新操作结束，等待其他任务更新完毕....");

                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
            /**
             * CyclicBarrier类提供了两种构造方法:
             * 第一种构造方法参数有:(int parties,Runnable barrierAction)
             *          parties表示让多少个线程等待至barrier状态
             *          barrierAction为当这些线程全部都为barrier状态后，执行的内容
             * 第二种构造方法参数有:(int parties)
             *
             */
        CyclicBarrier barrier=new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("***************************");
                System.out.println("所有线程都执行完了呀!!!!!");
            }
        });
        //添加个任务
        for (int i = 0; i < 5; i++) {
            new Thread(new UpdateTask(barrier)).start();
        }


    }
}
