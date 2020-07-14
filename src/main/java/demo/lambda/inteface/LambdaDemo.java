package demo.lambda.inteface;

/**
 * 类说明:lambda函数接口
 *
 * @author zengpeng
 */
public class LambdaDemo {

    public static void main(String[] args) {
        //i*2是函数式接口中抽象函数的具体实现
        Interface1 i1 = (i) -> i*2;
        Interface1.sub(10, 3);
        System.out.println(i1.add(3, 7));
        System.out.println(i1.doubleNum(20));

        Interface1 i2 = i -> i * 3;

        System.out.println(i2.doubleNum(2));
        Interface1 i3 = (int i) -> i * 2;

        Interface1 i4 = (int i) -> {
            System.out.println("-----");
            return i * 2;
        };

        System.out.println(i4.doubleNum(999));
    }
}
