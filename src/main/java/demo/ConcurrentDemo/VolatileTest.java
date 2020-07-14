package demo.ConcurrentDemo;

/**
 * 演示Volatile提供的可见性
 * @author 曾鹏
 */
public class VolatileTest {
    private volatile static  boolean ready;
    private static int number;


    private static class PrintThread extends  Thread{
        @Override
        public void run() {
            System.out.println("PrintThread is running....");
            while (!ready){  //无限循环
            }
            System.out.println("number="+number);
        }
    }

    public static void main(String[] args) throws InterruptedException{
        /**
         * 在下面的代码中，我们先启动了线程，在线程启动1秒后修改属性值,
         * 观察代码的控制台输出结果我们可以发现，当ready修改为true后，程序没有从死循环中退出。
         *
         * 接着，我们将ready使用volatile关键字进行修饰
         *
         * 在运行结果中，我们可以发现程序可以退出死循环了，这个就是volatile关键字的可见性了。
         * 关于内存模型导致的可见性原因，可以仔细阅读笔记
         */
        new PrintThread().start();
        Thread.sleep(1000);
        number=51;
        ready=true;
        Thread.sleep(5000);

        System.out.println("main is end!");

    }
}
