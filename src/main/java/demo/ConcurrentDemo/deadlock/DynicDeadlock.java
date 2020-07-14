package demo.ConcurrentDemo.deadlock;

/**
 * 动态的死锁,不安全的银行转账
 * @author 曾鹏
 */
public class DynicDeadlock implements Runnable{

    /**
     * 用于转账的一方的锁（谁转）
     */
    private Account from;


    /**
     * 收到转账的一方的锁（谁收）
     */
    private Account to;

    /**
     * 转账金额
     */
    private int money;

    /**
     * 第三把锁
     */
    private Object thirdLock=new Object();

    public DynicDeadlock(Account from, Account to, int money) {
        this.from = from;
        this.to = to;
        this.money = money;
    }


    @Override
    public void run() {
        doWithoutDeadlock();
    }

    /**
     * 会产生死锁的用法
     */
    public void doWithDeadlock(){
        synchronized (from){
            System.out.println(Thread.currentThread().getName()+"获得发起转账方的锁,准备获取收到转账方的锁...");
            synchronized (to){
                System.out.println(Thread.currentThread().getName()+"获得获取转账方的锁,开始转账...");
                from.setMoney(from.getMoney()-money);
                to.setMoney(to.getMoney()+money);
                System.out.println("转账成功");

            }
        }
    }

    /**
     * 不会产生死锁
     * 采用的解决方法,确定执行顺序
     */
    public void doWithoutDeadlock(){
        final int fromHash = System.identityHashCode(from);
        final int toHash = System.identityHashCode(to);
        if(fromHash>toHash){
            synchronized (from){
                System.out.println(Thread.currentThread()
                        .getName()+"获得发起转账方的锁,准备获取收到转账方的锁....");
                synchronized (to){
                    System.out.println(Thread.currentThread().getName()
                            +"获得获取转账方的锁,开始转账....");
                    from.setMoney(from.getMoney()-money);
                    to.setMoney(to.getMoney()+money);
                    System.out.println("转账成功");

                }
            }
        }else if (fromHash<toHash){
            synchronized (to){
                System.out.println(Thread.currentThread().getName()+"获得获取转账方的锁,准备获取收到转账方的锁...");
                synchronized (from){
                    System.out.println(Thread.currentThread().getName()+"获得发起转账方的锁,开始转账...");
                    from.setMoney(from.getMoney()-money);
                    to.setMoney(to.getMoney()+money);
                    System.out.println("转账成功");

                }
            }
        }else {
            synchronized (thirdLock){
                doWithDeadlock();
            }
        }
    }
}
