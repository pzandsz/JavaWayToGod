package demo.ConcurrentDemo.practice.demo1;

import algorithm.offer.Third;

import java.util.LinkedList;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class WriteReadQueueTest {
    public static void main(String[] args) throws InterruptedException {
//        LinkedList<Integer> data=new LinkedList<>();
//        for(int i=0;i<1;i++){
//            data.addLast(i);
//        }
//        WriteReadQueue writeReadQueue=new WriteReadQueue(data);

        WriteReadWithSemaphoreQueue writeReadQueue=new WriteReadWithSemaphoreQueue();

        new Thread(()->{
            try {
                for (int k=0;k<15;k++){
                    writeReadQueue.write(2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();



        new Thread(()->{
            try {
                for (int k=0;k<10;k++){
                    System.out.println(writeReadQueue.read());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
