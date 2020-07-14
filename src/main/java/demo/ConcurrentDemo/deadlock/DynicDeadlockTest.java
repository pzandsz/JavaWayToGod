package demo.ConcurrentDemo.deadlock;

/**
 * 测试动态的死锁 模拟转账
 * @author 曾鹏
 */
public class DynicDeadlockTest {
    public static void main(String[] args) {

        Account account1=new Account("张三",1000);
        Account account2=new Account("李四",1000);

        Thread threadA=new Thread(new DynicDeadlock(account1,account2,500), "A");
        Thread threadB=new Thread(new DynicDeadlock(account2,account1,500), "B");

        threadA.start();
        threadB.start();


        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account1.toString());
        System.out.println(account2.toString());
    }
}
