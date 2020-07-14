package demo.ConcurrentDemo.myThreadPool;

/**
 * 测试自己创建的线程池
 */
public class TestMyPool {
    public static void main(String[] args) throws InterruptedException {
        //创建容量为3的线程池
//        System.out.println(222);
        MyThreadPool pool=new MyThreadPool(3);

        //给一个等待时间，保证线程池的线程启动完之后再执行下一条语句
        Thread.sleep(5);

        for (int i=0;i<5;i++){
            TestMyPool.TestTask testTask=new TestMyPool.TestTask();
            Executor executor = pool.getExecutor();
            //设置任务
            executor.setTask(testTask);

            //唤醒
            executor.startTask();
        }

        pool.destroy();

    }

    /**
     * 任务对象
     */
    static class TestTask implements Task{

        @Override
        public void execute() throws InterruptedException {
            System.out.println(Thread.currentThread().getName()+"线程开始执行...");
            Thread.sleep(1000);
            System.out.println("处理结束.......");
            System.out.println("----------------------------------------------");
        }

        @Override
        public byte[] getResult() {
            return new byte[0];
        }
    }
}
