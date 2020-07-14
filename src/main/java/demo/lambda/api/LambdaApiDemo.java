package demo.lambda.api;

import java.util.function.Consumer;
import java.util.function.IntPredicate;

/**
 * 类说明:Lambda自带的接口函数
 *
 * @author zengpeng
 */
public class LambdaApiDemo {

    public static void main(String[] args) {
        // 断言函数接口
        IntPredicate predicate = i -> i > 0;
        System.out.println(predicate.test(-9));

        // IntConsumer
        // 消费函数接口
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("输入的数据");

    }
}
