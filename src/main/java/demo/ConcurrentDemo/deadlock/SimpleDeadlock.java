package demo.ConcurrentDemo.deadlock;

/**
 * 生成一个简单的死锁
 * @author 曾鹏
 */
public class SimpleDeadlock {

    /**
     * 锁1
     */
    private Object lock1=new Object();

    /**
     * 锁2
     */
    private Object lock2=new Object();

    /**
     * 先获得锁1，再获得锁2
     */
    public void doSomething12(){
        //获得锁1
        synchronized (lock1){
            System.out.println(Thread.currentThread().getName()+"获得了锁1，准备获得锁2....");
            synchronized (lock2){
                System.out.println(Thread.currentThread().getName()+"获得了锁2，开始处理业务...");
            }
        }
    }


    /**
     * 先获得锁2，再获得锁1
     */
    public void doSomething21(){
        //获得锁2
        synchronized (lock2){
            System.out.println(Thread.currentThread().getName()+"获得了锁2，准备获得锁1....");
            synchronized (lock1){
                System.out.println(Thread.currentThread().getName()+"获得了锁1，开始处理业务...");
            }
        }
    }


}
