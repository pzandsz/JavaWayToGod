package demo.ConcurrentDemo.practice.demo1;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * 类说明:通过信号量Semaphore来解决生产者消费者问题
 *
 *
 *   Dijkstra建议设立两种操作：down和up（分别为一般化后的sleep和wakeup）。
 * 对一信号量执行down操作，则是检查其值是否大于0。若该值大于0，则将其减1（即用掉一个保存的唤醒信号）并继续；
 * 若该值为0，则进程将睡眠，而且此时down操作并未结束。检查数值、修改变量值以及可能发生的睡眠操作均作为一个单一的、
 * 不可分割的原子操作完成。保证一旦一个信号量操作开始，则在该操作完成或阻塞之前，其他进程均不允许访问该信号量。
 * 这种原子性对于解决同步问题和避免竞争条件是绝对必要的。
 * 所谓原子操作，是指一组相关联的操作要么都不间断地执行，要么不执行。
 *
 * @author 曾鹏
 */
public class WriteReadWithSemaphoreQueue {

    private final int capactity=10;

    /**
     * 可读的信号量
     */
    private final Semaphore full=new Semaphore(0);
    /**
     * 可写的信号量
     */
    private final Semaphore empty=new Semaphore(capactity);


    /**
     * 互斥信号量
     */
    private final Semaphore mutex=new Semaphore(1);


    private LinkedList<Integer> integers=new LinkedList<Integer>();



    /**
     * 提供write方法
     */
    public void write(Integer data) throws InterruptedException {
        /**
         * 尝试获得可写的许可，如果许可已满，则阻塞当前线程
         * 尝试获得互斥的许可，容量为1,相当于一把锁
         */
        empty.acquire();
        mutex.acquire();

        System.out.println(Thread.currentThread().getName()+"将数据"+data+"写入容器中");
        integers.addLast(data);

        /**
         * 释放许可，相当于解锁
         */
        mutex.release();

        /**
         * 释放一个关于读的信号量许可，相当于唤醒一个读线程
         */
        full.release();

    }


    public Integer read() throws InterruptedException {
        Integer integer=null;

        /**
         * 尝试获得可读的许可，如果许可已满，则阻塞当前线程
         * 尝试获得互斥的许可，容量为1,相当于一把锁
         */
        full.acquire();
        mutex.acquire();


        integer = integers.getFirst();
        System.out.println(Thread.currentThread().getName()+"从容器中读取数据"+integer);
        /**
         * 释放许可，相当于解锁
         */
        mutex.release();

        /**
         * 释放一个关于写的信号量许可，相当于唤醒一个写线程
         */
        empty.release();
        return integer;
    }

}
