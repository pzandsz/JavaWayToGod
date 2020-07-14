package demo.ConcurrentDemo.CommenClass;

import java.util.concurrent.CyclicBarrier;

/**
 * 测试:并发编程常用类之CyclicBarrier
 * CyclicBarrier字面意思是环形栅栏，通过他可以实现让一组线程等待到某个状态后在全部同时执行。
 * 环形：因为当所有等待线程都被释放后,CyclicBarrier可以被重用。
 * @author 曾鹏
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        /**
         * CyclicBarrier类提供了两种构造方法:
         * 第一种构造方法参数有:(int parties,Runnable barrierAction)
         *          parties表示让多少个线程等待至barrier状态
         *          barrierAction为当这些线程全部都为barrier状态后，执行的内容
         * 第二种构造方法参数有:(int parties)
         *
         */
        CyclicBarrier cyclicBarrier=new CyclicBarrier(6);

        //将一组线程添加进同一个cyclicBarrier中
        for (int i=0;i<6;i++){
            new Thread(new WriteTask(cyclicBarrier)).start();
        }

    }

    /**
     * 执行"写"任务
     *
     */
    static class WriteTask implements Runnable{

        private CyclicBarrier barrier;

        public WriteTask(CyclicBarrier barrier){
            this.barrier=barrier;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"线程正在进行写操作!");

            //线程休眠3s,模拟写操作
            try {
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName()+"线程写入完毕!");
                /**
                 * 关于await()有两种重载方法:
                 *       await() :用来挂起当前线程，直至所有线程都到达barrier状态再同时执行后续任务；
                 *       await(long timeout, TimeUnit unit):这些线程等待至一定的时间，
                 *       如果还有线程没有到达sbarrier状态就直接让到达barrier的线程执行后续任务。
                 */
                barrier.await();


            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");

            System.out.println(Thread.currentThread().getName()+"线程开始处理第二个任务...");
            try {
                Thread.sleep(1000);
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("所有任务都处理完");

        }
    }
}
