package demo.ConcurrentDemo;

/**
 * 测试中断机制
 * 通过interrupt(),isInterrupted()实现的中断机制实际上是通过设置一个标识位来进行中断，
 * 所以在设计方法时应该给程序一个响应中断的操作，就比如下面的例子中,设置了while(isInterruptred())
 * 这种中断是协作式的，并不是强制执行的。
 * @author 曾鹏
 */
public class InterruptTest extends Thread{
    @Override
    public void run(){
        String threadName=Thread.currentThread().getName();
        System.out.println(threadName+" interrupt flag="+isInterrupted());

        while (!isInterrupted()){
            System.out.println(threadName+" is running");
            System.out.println(threadName+" inner interrupt flag="+isInterrupted());
        }
        System.out.println(threadName+" inner interrupt flag="+isInterrupted());

    }

    public static void main(String[] args) throws  InterruptedException{
        InterruptTest interruptTest=new InterruptTest();
        interruptTest.start();
        Thread.sleep(20);
        interruptTest.interrupt(); //中断线程，实际上是设置线程的标识位
    }
}
