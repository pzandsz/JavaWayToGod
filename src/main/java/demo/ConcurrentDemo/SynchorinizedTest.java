package demo.ConcurrentDemo;

/**
 * 错误的加锁
 * @author 曾鹏
 */
public class SynchorinizedTest {


    public static void main(String[] args) {
        Task task=new Task(1);

        for (int i = 0; i < 5; i++) {
            new Thread(task).start();
        }

    }
}
class Task implements  Runnable{

    private Integer value;

    private Object o=new Object();

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Task(Integer value) {
        this.value = value;
    }

    @Override
    public void run() {
        /**
         * synchronized是对对象进行加锁,如果需要使得下面的代码不出现错误,应该确保上锁的对象是不变的
         * 参考解决方案:创建一个Object实例并给他上锁
         */
        synchronized (o){
            //将value改成o
            Thread thread=Thread.currentThread();
            System.out.println(thread.getName()+"---@"+System.identityHashCode(value));
            /**
             *  首先，根据定义我们可以知道value是一个Integer类型的数据，是一个对象
             *  我们在Integer的源代码中可以发现value++的实现其实是 new Integer(value);这种新建了一个对象的方式
             *  在每次新建一个对象后,锁都失效了
             */
            value++;
            System.out.println(thread.getName()+"-----------------------"+value+"---@"+System.identityHashCode(value));
            try {
                Thread.sleep(1000);

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(thread.getName()+"-----------------------"+value+"---@"+System.identityHashCode(value));


        }
    }
}