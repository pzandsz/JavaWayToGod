package demo.lambda.link;

import java.text.DecimalFormat;
import java.util.function.Function;

/**
 * 类说明: 函数接口的链式操作
 *
 * @author zengpeng
 */
public class MoneyDemo {

    public static void main(String[] args) {
        Money money = new Money(456987123);

        Function<Integer,String> moneyFormat = (i)-> new DecimalFormat("#,###").format(i);

        //函数接口的链式操作
        money.printMoney(moneyFormat);
    }
}
