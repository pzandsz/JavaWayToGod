package demo.ConcurrentDemo.myThreadPool;

import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

/**
 * 创建一个自己的线程池
 * 存在的问题：在销毁线程池时，会有些因为lock.wait所等待的线程无法被唤醒
 *
 *
 * 解决的问题：针对将线程使用等待通知模式的锁一定要是同一把锁.
 * @author 曾鹏
 */
public class MyThreadPool implements Pool{

    /**
     * 用于表示池的状态:true表示销毁 false表示存活
     */
    private boolean  isShut;
    /**
     * 用于存储执行器(工作线程)的容器
     */
    private LinkedList<Executor> pool;


    /**
     * 用于将任务进入等待的一把锁
     */
    private  Object lock =  new  Object();

    /**
     * 线程池的大小
     */
    private  int  size;

    public boolean isShut() {
        return isShut;
    }

    /**
     * 构造方法
     * @param size
     */
    public MyThreadPool(int size){
        this.size=size;
        //将线程池设置为存活状态
        isShut = false;


        //初始化工作线程容器
        pool=new LinkedList<Executor>();
        for (int i=0;i<size;i++){
            //获得执行器(工作线程)
            Executor executor =  new  ExecutorImpl();
            //将工作线程放入到池容器中
            pool.add(executor);

            //将工作线程启动
            ((ExecutorImpl) executor).start();
        }

    }

    /**
     * 返回获得的工作线程
     * @return Executor|
     * @throws InterruptedException
     */
    @Override
    public Executor getExecutor() throws InterruptedException {
        Executor executor=null;
        synchronized (pool){
            if(pool.size()<=0){
                /**
                 * 当池中没有空闲的工作线程(执行器)时，将当前线程等待
                 */
                //执行在这条语句的线程将进入等待状态，知道调用pool.notify()或者pool.notifyAll()
//                System.out.println(1);
                pool.wait();
                executor=pool.removeFirst();
            }else {
                executor=pool.removeFirst();
            }

        }
        return executor;
    }

    /**
     * 将池销毁
     */
    @Override
    public void destroy() {
        synchronized (pool){
            //修改存活状态
            isShut =  true ;
            //将所有等待中(通过pool.wait()进入等待的线程)的线程唤醒
            pool.notifyAll();
            //清除pool对象
            pool.clear();

            //如何清理执行lock.wait()进入等待状态的任务
        }
    }

    /**
     * 工作线程对象，私有对象
     */
    private class  ExecutorImpl extends Thread implements Executor{

        /**
         * 任务对象
         */
        private  Task task;

        public  ExecutorImpl(){}

        /**
         * 获得任务对象
         * @return
         */
        @Override
        public Task getTask() {
            return this.task;
        }

        /**
         * 设置任务对象
         * @param task
         */
        @Override
        public void setTask(Task task) {
            this.task = task;
            startTask();
        }

        /**
         * 通知范式
         * 唤醒任务对象
         */
        @Override
        public void startTask(){
            synchronized(lock){
                /**
                 * 执行这条语句后将随机唤醒一个任务
                 * 如果只有一个任务，就是将指定任务唤醒
                 *
                 */
                System.out.println("唤醒一个任务");
                lock.notify();
            }
        }

        /**
         * 唤醒所有线程
         */
        public   void  endTask(){
            synchronized (lock){
                /**
                 * 执行这条语句后将随机唤醒一个任务
                 * 如果只有一个任务，就是将指定任务唤醒
                 */
                System.out.println("唤醒所有线程");
                lock.notifyAll();
            }
        }


        /**
         * 等待范式
         */
        @Override
        public   void  run(){
            /**
             * 判断当前池的状态
             */
            while (!isShut){
                synchronized (lock){
                    try  {
                        //执行这个语句的线程进入等待状态
                        System.out.println(Thread.currentThread().getName()+"线程等待");
                        lock.wait();
                        System.out.println(Thread.currentThread().getName()+"线程正在处理任务"+task.toString());
                        //执行任务对象中的任务
                        getTask().execute();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                synchronized (pool){
                    /**
                     * 将执行完的任务对象的工作线程放回到工作线程池中
                     * 接着唤醒所有在等待的线程(告诉他们有空闲的工作线程了)
                     */
                    pool.addFirst(ExecutorImpl.this );
                    pool.notifyAll();
                }


            }


            synchronized (lock){
                lock.notifyAll();
            }

//            if(isShut){
//                endTask();
//                return;
//            }
        }

    }

}
