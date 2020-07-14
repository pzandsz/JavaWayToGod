package demo.ConcurrentDemo.Lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试Lock中的tryLock()和tryLock(long time,TimeUnit unit)方法
 * @author 曾鹏
 */
public class LockMethodTest {

    private ArrayList<Integer> arrayList=new ArrayList<>();

    Lock lock=new ReentrantLock();

    public void insert(Thread thread) {

        /**
         * 通过tryLock()尝试获取锁
         * 若果没有获得锁就会返回false,如果获得锁就会返回true
         */
        if(lock.tryLock()){
            try{
                System.out.println(thread.getName()+"线程获得了锁");
                //对共享变量进行处理,每次增加五个长度的变量
                for (int i=0;i<5;i++){
                    arrayList.add(i);
                }
                System.out.println("共享变量修改成功!");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println(thread.getName()+"线程释放了锁");
                System.out.println("-----------------------------------");
                lock.unlock();
            }
        }else {
            System.out.println("\n"+"未获得线程，等待释放......");
        }

    }


    public static void main(String[] args) {
        final LockMethodTest lockMethodTest=new LockMethodTest();

        for (int i=0;i<100;i++){
            //模拟线程A修改共享变量
            new Thread("A"){

                @Override
                public void run() {
                    lockMethodTest.insert(Thread.currentThread());
                }
            }.start();

            //模拟线程B修改共享变量
            new Thread("B"){

                @Override
                public void run() {
                    lockMethodTest.insert(Thread.currentThread());
                }
            }.start();


        }
    }

}
