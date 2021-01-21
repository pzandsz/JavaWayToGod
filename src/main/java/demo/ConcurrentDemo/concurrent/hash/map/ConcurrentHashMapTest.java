package demo.ConcurrentDemo.concurrent.hash.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentHashMapTest extends Thread{


    static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>(16);
    static AtomicInteger at = new AtomicInteger(1);

    public void run()
    {
        if (Thread.currentThread().getName().equals("thread0")) {
            System.out.println("thread 0");
            for (int i = 1; i < 1000000; ++i){
                map.put(28 + (i << 5), 28 + (i << 5));
                map.put(21 + (i << 5), 21 + (i << 5));
            }
        } else if (Thread.currentThread().getName().equals("thread1")){
            System.out.println("thread 1");
            for (int i = 1; i < 1000000; ++i){
                map.put(29 + (i << 5), 29 + (i << 5));
                map.put(22 + (i << 5), 22 + (i << 5));
            }
        }
    }

    public static void main(String[] args) {
        ConcurrentHashMapTest t0 = new ConcurrentHashMapTest();
        t0.setName("thread0");
        ConcurrentHashMapTest t1 = new ConcurrentHashMapTest();
        t1.setName("thread1");
//        CHMTest t2 = new CHMTest();
//        CHMTest t3 = new CHMTest();
        t0.start();
        t1.start();
//        t2.start();
//        t3.start();
    }

}
