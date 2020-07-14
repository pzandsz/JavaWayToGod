package demo.ConcurrentDemo.CAS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 乐观锁:“尔等都是忠臣，朕相信你们不会害我”,不进行加锁操作，而是基于自旋锁实现，并且通过
 * 原子操作类进行判断ABA问题
 *
 *
 * 测试：在测试类中，我们使用了两个变量，i和p，其中i是原子操作类对象而j是正常的整型变量
 * 测试结果显示:i的累加和是正确的，p的累加和是错误的
 *
 * @author 曾鹏
 */
public class CasLock {
    /**
     * 等待最多五个线程执行完毕
     */
    private static final CountDownLatch latch = new CountDownLatch(5);
    /**
     * 原子操作类
     * 原子操作类中的调用了Unsafe类的getAndAddInt()方法，该方法内进行了自旋操作
     * this.compareAndSwapInt(var1, var2, var5, var5 + var4)就是用于判断是否进行自旋。
     *
     *
     * 解释一下getAndAddInt方法的流程
     *假设有以下情景：
     *      1.A、B两个线程
     *      2.jvm主内存的值1，A、B工作内存的值为1（工作内存会拷贝一份主内存的值）
     *      3.当前期望值为1，做加1操作
     *      4.此时var5 = 1, var4 = 1,
     *           （1）A线程将var5与工作内存值M比较，比较var5是否等于1
     *           （2）如果相同则将工作内存值修改为var5+var4 既修改为2并同步到主内存，此时this指针里，
     *            示例变量value的值就是2，结束循环
     *           （3）如果不相同则其B线程修改了主内存的值，说明B线程已经先于A线程做了加1操作，
     *            A线程没有更新成功需要继续循环，注意此时var5更新为新的内存值，假设当前的内存值是2，那么此时var5 = 2， var5 + var4 = 3,重复上述步骤直到成功
     *
     */
    private static AtomicInteger i = new AtomicInteger(0);

    private static int p = 0;

    public static void main(String[] args) throws InterruptedException {
        long time = System.currentTimeMillis();
        //获得线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        for(int j = 0; j < 5; j++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    for(int k = 0; k < 10000; k++) {
                        p++;                //不是原子操作
                        i.getAndIncrement();//调用原子类加1
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();//保证所有子线程执行完成
        System.out.println(System.currentTimeMillis() - time);
        System.out.println("p=" + p);
        System.out.println("i=" + i);
        pool.shutdown();
    }
}
