package demo.util;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 测试id生成器
 */
public class SonwFlakeIdUtilTest {

    CountDownLatch start=new CountDownLatch(1);
    CountDownLatch latch=new CountDownLatch(1000);
    SnowFlakeIdUtil test=new SnowFlakeIdUtil(0,0);


    Object obj=new Object();

    ConcurrentHashMap<Long,Object> idMap=new ConcurrentHashMap<>();



    class TestTask implements Runnable{
//基于CountDownLatch实现
        @Override
        public void run() {

            try {
                //等待发令枪响
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("放入map中");
            idMap.put(test.nextId(),obj);
//            System.out.println(Thread.currentThread().getName()+test.nextId());

            //该CountDownLatch对象是用于控制最后两个的输出一直在线程全部执行完毕后
            latch.countDown();
        }
    }


//    public  void testSynchronized(){
//        ArrayList<Thread> threads=new ArrayList<>();
//        for (int i=0;i<1000;i++){
//            if(i==999){
//                new Thread(new TestTask(threads,1)).start();
//            }else {
//                new Thread(new TestTask(threads,0)).start();
//            }
//
////        }
//
//        System.out.println(idMap.size());
//
//    }

    public void test() throws InterruptedException {

        for (int i=0;i<1000;i++){
            new Thread(new TestTask()).start();
        }

        System.out.println("准备10秒");
        Thread.sleep(10000);
        //打响发令枪
        start.countDown();


        //确保线程执行完之后在打印下面两句话
        latch.await();

        System.out.println(idMap.size());
        System.out.println("---------结束---------");
    }

    public static void main(String[] args) throws InterruptedException {
        SonwFlakeIdUtilTest utilTest=new SonwFlakeIdUtilTest();
        utilTest.test();
    }
}
