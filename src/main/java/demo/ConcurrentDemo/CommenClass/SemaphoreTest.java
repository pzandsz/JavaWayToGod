package demo.ConcurrentDemo.CommenClass;

import java.util.concurrent.Semaphore;

/**
 * 测试:Semaphore信号量，Semaphore可以控制同时访问的线程个数,通过acquire()获得一个许可，
 * 如果没有就等待，而release()就释放一个许可
 * @author 曾鹏
 * 测试场景:假若一个工厂有5台机器，但是有8个工人，一台机器同时只能被一个工人使用，
 * 只有使用完了，其他工人才能继续使用。
 *
 *
 *
 * 在使用Semaphore信号量机制是，需要注意的一点：当许可没有被占用时，有人使用的relase()方法释放了一个
 * 许可后，会导致许可数量的增加.
 *
 *
 *
 * 即使创建信号量的时候，指定了信号量的大小。
 * 但是在通过 release()操作释放信号量任然能超过配置的大小。
 * 也就有可能同时执行的线程数量比最开始设置的要大。
 * 没有任何线程获取信号量的时候，依然能够释放并且释放的有效。
 */
public class SemaphoreTest {

    static class Worker implements Runnable{

        private int num;
        private Semaphore semaphore;

        public Worker(int num,Semaphore semaphore){
            this.num=num;
            this.semaphore=semaphore;
        }


        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人"+this.num+"占用一台机器在生产....");
                //模拟生产过程
                Thread.sleep(2000);

                System.out.println("工人"+this.num+"释放一台机器!");

                semaphore.release();
            }catch (Exception e){

            }
        }
    }

    public static void main(String[] args) {
        //工人数
        int N = 8;
        //机器数目
        Semaphore semaphore = new Semaphore(5);

        for(int i=0;i<N;i++){
            new Thread(new Worker(i,semaphore)).start();
        }

    }
}
