package demo.ConcurrentDemo.pool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据库连接处测试类
 * @author 曾鹏
 */
public class ConnectionPoolTest {
    /**
     *  初始化10个连接
     */
    static ConnectionPool pool=new ConnectionPool(10);

    static CountDownLatch start=new CountDownLatch(1);

    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        int threadCount=20;
        end=new CountDownLatch(threadCount);
        int count=20;

        //获得连接数
        AtomicInteger got = new AtomicInteger();
        //未获得连接数
        AtomicInteger nogot=new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            Thread thread=new Thread(new ConnectionRunner(count,got,nogot),"ConnectionRunnerThread");
            thread.start();
        }

        start.countDown();
        end.await();
        System.out.println("total invoke: "+(threadCount*count));
        System.out.println("got connection "+got);
        System.out.println("not got connection "+nogot);

    }

    static class ConnectionRunner implements  Runnable{

        int count;
        AtomicInteger got;
        AtomicInteger noGot;

        public ConnectionRunner(int count,AtomicInteger got,AtomicInteger noGot){
            this.count=count;
            this.got=got;
            this.noGot=noGot;
        }

        @Override
        public void run() {

            try {
                start.await();
            }catch (Exception e){

            }

            while (count>0){
                try {
                    Connection connection= pool.fetchConnection(1000);
                    if(connection!=null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        }finally {
                            //释放连接
                            pool.relaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }else {
                        noGot.incrementAndGet();
                    }
                }catch (Exception e){ }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
