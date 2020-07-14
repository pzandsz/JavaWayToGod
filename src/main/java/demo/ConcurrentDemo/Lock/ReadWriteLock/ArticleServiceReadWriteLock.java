package demo.ConcurrentDemo.Lock.ReadWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 通过ReadWriteLock实现读写操作
 */
public class ArticleServiceReadWriteLock {

    private ReentrantReadWriteLock lock=new ReentrantReadWriteLock();

    /**
     * 读锁
     */
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    /**
     * 写锁
     */
    private ReentrantReadWriteLock.WriteLock writeLock=lock.writeLock();


    /**
     * 读操作
     */
    public void read(){
        readLock.lock();
        try {
            //模拟读操作
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readLock.unlock();
        }
    }

    /**
     * 写操作
     */
    public void write(){
        writeLock.lock();
        try {
            //模拟写操作
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

}
