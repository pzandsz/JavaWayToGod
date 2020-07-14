package demo.ConcurrentDemo.practice.demo1;

import java.util.LinkedList;


/**
 * 类说明:读写队列，现在存在这样一个队列，可以从这个队列的首部获取数据，在这个队列的尾部写入数据
 *
 * 现在有两个线程，一个每隔一段时间会去这个队列的首部读取一个数据来处理，并一个会相对列的尾部插入数据
 * 请你设计一个这样的队列，并确保这个队列四线程安全的.
 *
 * @author 曾鹏
 */
public class WriteReadQueue {
    /**
     * 问题分析：因为情景中只有两个线程，并且两个线程的处理相互影响的
     * 1.存储队列的容器的设计，队列的容量先设计为固定的，不考虑扩容的情况
     *
     * 2.设置两个信号量，用来表示当前可读数量以及可写数量
     */

    private volatile int readSize;
    private volatile int writeSize;

    private Object readLock=new Object();
    private Object writeLock=new Object();

    private volatile boolean readWakeup=false;
    private volatile boolean writeWakeup=false;


    private LinkedList<Integer> integers;

    /**
     * @param list
     */
    public WriteReadQueue(LinkedList<Integer> list){
        this.integers=list;
        this.readSize=list.size();
        this.writeSize=0;
    }


    /**
     * 提供write方法
     */
    public void write(Integer data) throws InterruptedException {
        while (true){
            if(writeSize>0){
                integers.addLast(data);
                writeSize--;
                readSize++;
                //唤醒等待中的读线程
                synchronized (readLock){
                    System.out.println("成功写入:"+data);
                    readWakeup=true;
                    readLock.notifyAll();
                }
                break;
            }else {
                synchronized (writeLock){
                    if(!writeWakeup){
                        System.out.println("等待写入:"+data);
                        writeLock.wait();
                    }
                }
            }
        }
    }

    /**
     * 提供读方法
     *  存在一个死锁:当readSize等于0时，此时CPU调度结束，还未来得及将读操作进入阻塞状态，
     *  此时如果写入一个数据将readSize改为1,写操作发起一个唤醒操作，但这个唤醒操作并没有唤醒读操作
     *  之后，继续进行上一步的操作，将读操作放入阻塞状态，
     *
     *  因为容量为1，在进行写操作的时候会将写操作进入阻塞状态，
     *  此时写等着读唤醒，读等着写唤醒，进行死锁
     *
     *
     *  解决方法，将判断是否readSize为0与进入阻塞状态封装成一个原子操作
     *  因为这两个操作在java中没有提供封装方法，所以只能另谋他途
     *
     *  增加一个唤醒通知位，（这只是补救措施，并没有从根源解决问题）
     *
     *
     *  这种问题究其本质是一个wakeup(唤醒)丢失的问题，其实为了解决这种问题，juc包中提供了一个Semaphore类来解决
     *  在semaphore中，通过将上述的操作封装成一个原子操作，从而解决了wakeup丢失的问题。
     *  （关于Semaphore是如何解决这种问题的处理方法，这个问题需要深入到Semaphore的源码中去分析）
     *
     *  实际上还有另外一种方式解决上面的wakeup丢失的问题，那就是利用synchronized关键字，将上述的操作都封装到一个
     *  synchronized关键字修饰的方法或者代码块中。
     *  因为synchronized在操作系统中是通过monitorenter和monitorexit这两个方法来实现加锁解锁
     *  而monitor是一个管程，也就是说synchronized的底层是通过管程来实现的，查看《现代操作系统》，我们可以知道
     *  管程在设计上就解决了wakeup丢失的问题，在monitorenter和monitorexit之间的代码必须被全部执行，才能切换到其他线程
     *  利用这个特性，我们就能够解决上面的wakeup丢失的问题!!!
     */
    public Integer read() throws InterruptedException {
        Integer integer=null;

        while (integer==null){
            if(readSize>0){
                integer= integers.removeFirst();
                writeSize++;
                readSize--;
                //互相等待中的写线程
                synchronized (writeLock){
                    writeWakeup=true;
                    writeLock.notifyAll();
                }
            }else {
                //将当前线程进入等待
                synchronized (readLock){
                    if(!readWakeup){
                        readLock.wait();
                    }

                }
            }
        }
        return integer;
    }


}
