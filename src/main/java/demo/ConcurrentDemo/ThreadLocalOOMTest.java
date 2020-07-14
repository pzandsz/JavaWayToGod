package demo.ConcurrentDemo;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 类说明：ThreadLocal造成的内存泄露演示
 * @author 曾鹏
 */
public class ThreadLocalOOMTest {
    private static final int TASK_LOOP_SIZE=500;

    final static ThreadPoolExecutor poolExecutor=new ThreadPoolExecutor(5,
            5,1000,TimeUnit.MINUTES,new LinkedBlockingQueue<>());

    static class LocalVariable{
        //5M大小的数组
        private byte[] a=new byte[1204*1204*5];

    }

    final static ThreadLocal<LocalVariable> localVariable=new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException{
        for (int i = 0; i < TASK_LOOP_SIZE; i++) {
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    /**
                     * 第一种测试情况:只在线程中new一个LocalVariable
                     * 这种情况下，程序的内存占用大约为25M
                     * 第二种测试情况:使用localVariable.set(new LocalVariable())设置一个LocalVariable对象
                     * 这种情况下，程序的内存占用大概为150M
                     * 第三中测试情况:在第二种测试的前提之下，使用localVariable.remove()
                     * 这种情况下，程序的内存占用又恢复到了25M左右
                     *
                     * 那么，在第二种情况下，消失的那些内存去哪了呢?(内存泄漏)为什么会产生这种情况呢?
                     * 产生内存泄漏的原因是因为：在ThreadLocalMap中的Entity中的key,value两个属性，
                     * Entity是以ThreadLocal为Key,用户传入的对象为value,而ThreadLocal是一个弱引用，
                     * 弱引用在每次垃圾回收的时候就会被回收，这样Entity的key就被回收了，而value还存在
                     * 一个强引用，不会被回收，但是又无法通过key获得到value值，这样就造成了一个内存泄漏。
                     * 更多细节可参见笔记
                     */
                    localVariable.set(new LocalVariable());
//                    new LocalVariable();
                    System.out.println("use local variable");

                    localVariable.remove();
                }
            });

            Thread.sleep(100);
        }
        System.out.println("pool execute over");
    }

}

