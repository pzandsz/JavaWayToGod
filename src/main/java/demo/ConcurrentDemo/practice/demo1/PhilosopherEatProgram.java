package demo.ConcurrentDemo.practice.demo1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 类说明:经典多线程问题之哲学家就餐
 * 五个哲学家围在一张圆桌周围，每个哲学家面前有一碗炒粉，吃炒粉必须要有两根筷子，
 * 相邻两个盘子之间有一根筷子。哲学家有两种交替活动时段:即吃饭和思考，
 * 当一个哲学家饿了，他就会试图去取左右两边的筷子(顺序不定),如果成功拿到筷子，则开始吃炒粉
 * 吃完之后放下筷子继续思考。那么问题来了:能为每个哲学家写一段描述其行为的代码，且绝不会出现死锁吗?
 *
 *
 *
 * 解决思路:每位哲学家在就餐时查看他的左右两边的哲学家是否处于进餐状态
 *
 * @author 曾鹏
 */
public class PhilosopherEatProgram {

    private static final int NUM=5;
    /**
     * 0表示哲学家在思考，1表示哲学家试图拿筷子，2表示哲学家在进餐
     */
    private static final int THINKING=0;
    private static final int GET=1;
    private static final int EAT=2;

    /**
     * 表示五个哲学家的当前状态
     */
    private volatile int[] states=new int[NUM];

    /**
     * 用于锁住筷子
     */
    ReentrantLock lock=new ReentrantLock();

    /**
     * 哲学家i准备进餐
     */
    public void eat(int i){

        //当前哲学家为准备拿筷子状态
        states[i]=GET;
        System.out.println("-------------------"+i+"---------------哲学家"+i+"试图拿筷子准备吃炒粉.......");
        //获得筷子并吃炒粉
        getChopsticksAndEat(i);

        //吃完炒粉
        states[i]=THINKING;
        System.out.println("-------------------"+i+"---------------哲学家"+i+"进入思考状态....");
    }

    /**
     * 第i位哲学家获得筷子,成功拿到便开始吃炒粉
     * @param i
     */
    public void getChopsticksAndEat(int i){
        lock.lock();
        try{
            //使用一个自旋锁，不断尝试查看是否可以获得筷子,没有将未获得筷子的线程直接阻塞
            while (states[i]!=EAT){
                if(states[i]==GET&&states[(i+NUM-1)%NUM]!=EAT&&states[(i+NUM+1)%NUM]!=EAT){
                    System.out.println("哲学家"+i+"开始吃炒粉.......");
                    //开始吃炒粉
                    states[i]=EAT;
                    //吃了1秒钟
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("哲学家"+i+"等待....");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        ExecutorService pool= Executors.newFixedThreadPool(5);

        PhilosopherEatProgram program=new PhilosopherEatProgram();

        for (int i=0;i<5;i++){
            pool.execute(new EatTask(i,program));
        }
    }



    static class EatTask implements Runnable{

        int i;
        PhilosopherEatProgram program;

        public EatTask(int i,PhilosopherEatProgram program){
            this.i=i;
            this.program=program;
        }

        @Override
        public void run() {
            program.eat(i);
        }
    }
}
