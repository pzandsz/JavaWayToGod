package demo.ConcurrentDemo.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据库连接池
 * @author 曾鹏
 */
public class ConnectionPool {

    //连接池对象
    private LinkedList<Connection> pool=new LinkedList<>();

    /**
     * 初始化连接池
     * @param initialSize
     */
    public ConnectionPool(int initialSize){
        if(initialSize>0){
            //添加initialSize个Connection对象
            for (int i = 0; i < initialSize; i++) {
                pool.add(ConnectionDriver.createConnection());
            }
        }
    }

    /**
     * 通知方范式
     * 释放连接，连接池回收连接
     * @param connection
     */
    public void relaseConnection(Connection connection){
        if(connection!=null){
            synchronized (pool){
                pool.add(connection);
                //唤醒所有等待
                pool.notifyAll();
            }
        }
    }

    /**
     * 等待方范式以及基于超时的等待方范式
     * @param mills:等待时长
     * @return 返回连接对象
     * @throws InterruptedException
     */
    public Connection fetchConnection(long mills) throws InterruptedException{
        /**
         * 第一步:获得对象的锁
         * 第二步:判断mills是否<=0
         * 若mills<=0，使用不带等待超时模式的通知范式
         * 若mills>0,使用基于等待超时模式的通知范式:
         *      每次判断连接池是否为空的同时，判断一下是否超时
         *      若超时，直接返回数据(若拿到连接就返回Connection对象，否则返回null)
         *      若未超时，循环尝试获取连接池连接
         *
         *
         * 附加:需要注意的是我们每次使用synchronized对对象进行加锁时，
         * 只是对一个对象(在这个案例里，这个对象是pool)赋予了去锁的含义,
         * 每一个执行了pool.await()的线程都会阻塞到一个等待队列中，等到调用pool.notify()
         * 或者pool.notifyAll()方法后，才会将线程从阻塞队列中取出
         *
         *
         * notify():随机唤醒一个线程
         * notifyAll():唤醒所有线程
         */
        synchronized (pool){
            if(mills<=0){
                //当传入的mills<=0时，使用不带等待超时的哦通知范式
                while (pool.isEmpty()){
                    //当连接池为空时,线程等待
                    pool.wait();
                }
                return  pool.removeFirst();
            }else {
                long future=System.currentTimeMillis()+mills;
                long remaining=mills;
                while (pool.isEmpty()&&remaining>0){
                    pool.wait(remaining);
                    //及时更新等待时间remaining
                    remaining=future-System.currentTimeMillis();
                }


                Connection connection=null;
                if(!pool.isEmpty()){
                    connection=pool.removeFirst();
                }
                return connection;
            }
        }
    }
}
