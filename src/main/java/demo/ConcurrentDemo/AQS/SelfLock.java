package demo.ConcurrentDemo.AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 通过AQS实现我们自己的独占锁
 * @author 曾鹏
 */
public class SelfLock implements Lock {
    /**
     * 静态内部类，实现同步器
     */
    private static class Sync extends AbstractQueuedSynchronizer{
        /**
         * 判断当前锁是否处于占用状态
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        /**
         * 获得锁
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            /**
             * compareAndSetState(0, 1)是一个原子操作，
             * 其代表的是如果原来的值是0那就将其设为1，并且返回ture。
             * 那这个原来的值是指的谁的值呢？从compareAndSetState中并看不出来。
             * 那就从整体来看，在AQS中有个表示当前锁的状态的int值state,
             * 当state等于0时，表示锁可用，否则表示锁定状态，是否可用还需考虑其他情况如可重入性。
             * compareAndSetState(0, 1)就是设置这个state的状态
             */
            if(compareAndSetState(0,1)){

                /**
                 * Sets the thread that currently owns exclusive access.
                 * 设置当前拥有独占访问权的线程
                 */
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 释放锁
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            if(getState()==0){
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    final Sync sync=new Sync();

    @Override
    public void lock(){
        System.out.println(Thread.currentThread().getName()+"准备获得锁");
        sync.tryAcquire(1);
        System.out.println(Thread.currentThread().getName()+"已经获得锁");
    }

    @Override
    public boolean tryLock(){
        return sync.tryAcquire(1);
    }

    @Override
    public void unlock(){
        System.out.println(Thread.currentThread().getName()+"准备释放锁");
        sync.tryRelease(1);
        System.out.println(Thread.currentThread().getName()+"已经释放锁");
    }

    @Override
    public Condition newCondition(){
        return sync.newCondition();
    }


    public boolean isLock(){
        return sync.isHeldExclusively();
    }


    public boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException{
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException{
        return sync.tryAcquireNanos(1,unit.toNanos(timeout));
    }
}
