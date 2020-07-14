package demo.ConcurrentDemo;

/**
 * 测试ThreadLocal类
 * @author 曾鹏
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocalTask task=new ThreadLocalTask();

        for (int i=0;i<5;i++){
            //启动线程
            new Thread(task).start();
        }
    }
}


class ThreadLocalTask implements  Runnable{

    /**
     * threadLocalInteger:1
     */
    ThreadLocal<Integer> threadLocalInteger=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };


    /**
     * threadLocalString的初始值为"ThreadLocal"
     * 相当于String str="ThreadLcoal"; 但是这个str是只对当前线程可见的
     */
    ThreadLocal<String> threadLocalString=new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "ThreadLocal";
        }
    };

    @Override
    public void run() {
        synchronized (threadLocalInteger){
            Thread thread =Thread.currentThread();
            System.out.println("线程"+thread.getName()+",integer初始值:"+threadLocalInteger.get());
            System.out.println("线程"+thread.getName()+",string初始值:"+threadLocalString.get());

            threadLocalInteger.set(5);
            threadLocalString.set("Welcome!");

            System.out.println("线程"+thread.getName()+",integer修改后:"+threadLocalInteger.get());
            System.out.println("线程"+thread.getName()+",string修改后:"+threadLocalString.get());


        }
    }
}