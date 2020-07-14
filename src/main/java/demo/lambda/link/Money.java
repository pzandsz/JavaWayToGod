package demo.lambda.link;

import java.util.function.Function;

/**
 * 类说明:钱
 *
 * @author zengpeng
 */
public class Money {
    private final int money;

    public Money(int money){
        this.money = money;
    }

    public void printMoney(Function<Integer, String> moneyFormat) {
        System.out.println("我的存款" + moneyFormat.apply(this.money));
    }

}
