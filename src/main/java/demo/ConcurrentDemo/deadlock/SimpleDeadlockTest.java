package demo.ConcurrentDemo.deadlock;

/**
 * 测试简单的死锁(因为执行顺序造成的)
 *      A获得了锁1，准备获得锁2....
 *      B获得了锁2，准备获得锁1....
 * 上面的情况就是死锁发生时的输出,A持有锁1去获得锁2，B持有锁2去获得锁1，从而造成死锁
 * 解决方案:将doSomething12()和doSomething21()都规定为先获得锁1在获得锁2，强制执行顺序
 * @author 曾鹏
 */
public class SimpleDeadlockTest {
    public static void main(String[] args) {

        SimpleDeadlock deadlock=new SimpleDeadlock();

        Thread thread1=new Thread("A"){
            @Override
            public void run(){
                deadlock.doSomething12();
            }
        };

        Thread thread2=new Thread("B"){
            @Override
            public void run(){
//                deadlock.doSomething21();
                deadlock.doSomething12();
            }
        };

        //启动线程
        thread1.start();
        thread2.start();
    }
}
