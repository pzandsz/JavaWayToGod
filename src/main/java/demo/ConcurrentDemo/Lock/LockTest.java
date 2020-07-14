package demo.ConcurrentDemo.Lock;

import sun.awt.windows.ThemeReader;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试Lock接口
 * @author 曾鹏
 */
public class LockTest {

    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    /**
     * new ReentrantLock(true):就表示创建一个公平锁
     *
     * isFair() //判断锁是否是公平锁
     *
     * isLocked() //判断锁是否被任何线程获取了
     *
     * isHeldByCurrentThread() //判断锁是否被当前线程获取了
     *
     * hasQueuedThreads() //判断是否有线程在等待该锁
     *
     * getHoldCount() //查询当前线程占有lock锁的次数
     *
     * getQueueLength() // 获取正在等待此锁的线程数
     *
     * getWaitQueueLength(Condition condition) // 获取正在等待此锁相关条件condition的线程数
     *
     */
    private Lock lock=new ReentrantLock();

    public void insert(Thread thread){

        /**
         * 创建一个ReentrantLock对象进行测试,此时的Lock是一个局部变量
         * 即每一次调用insert方法就会创建一个实现了Lock接口的类对象,
         * 这就意味着，对共享变量上的锁每次都不是同一把锁，这样就会导致下面的结果:
         *      B线程获得了锁
         *      A线程获得了锁
         *      共享变量修改成功!
         *      共享变量修改成功!
         *      A线程释放锁!
         *      B线程释放锁!
         * 这种结果说明对于共享变量的加锁操作失效了，线程A在线程B还没有释放锁的时候就获得了锁，
         * 导致共享变量的不安全，为了解决这种情况，我们只要让多个线程竞争同一把锁就可以，
         * 也就是将Lock接口的实例定义为成员变量即可。
         */
//        Lock lock=new ReentrantLock();
        //使用lock()方法获得锁
        lock.lock();

        try {
            System.out.println(thread.getName()+"线程获得了锁");
            //对共享变量进行处理,每次增加五个长度的变量
            for (int i=0;i<5;i++){
                arrayList.add(i);
            }
            System.out.println("共享变量修改成功!");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(thread.getName()+"线程释放锁!");
            //释放锁
            lock.unlock();
            System.out.println("-----------------------------------------");
        }
    }

    public static void main(String[] args) {
        final LockTest lockTest=new LockTest();

        for (int i=0;i<100;i++){
            //模拟线程A修改共享变量
            new Thread("A"){

                @Override
                public void run() {
                    lockTest.insert(Thread.currentThread());
                }
            }.start();

            //模拟线程B修改共享变量
            new Thread("B"){

                @Override
                public void run() {
                    lockTest.insert(Thread.currentThread());
                }
            }.start();


        }
    }

}
