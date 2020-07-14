package demo.lambda.inteface;

/**
 * @FunctionalInterface: 用于编译级错误检查，加上该注解，当你的接口不符合函数式接口的定义时，编译器会报错
 *
 * 函数式接口：在该接口中只能有一个抽象方法
 */
@FunctionalInterface
public interface Interface1 {
    double doubleNum(int num);
//    double doubleNum1(int num);

    default int add(int x,int y){
        return x+y;
    }

    static int sub(int x,int y){
        return x-y;
    }
}
