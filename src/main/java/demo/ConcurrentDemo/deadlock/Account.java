package demo.ConcurrentDemo.deadlock;

/**
 * 账号信息
 * @author 曾鹏
 */
public class Account {

    private String name;
    private volatile int money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    public Account(String name, int money) {
        this.name = name;
        this.money = money;
    }
}
