package demo.lambda.inteface;

/**
 * 类说明:
 *
 * @author zengpeng
 */
@FunctionalInterface
public interface  Inteface3 extends Interface1,Interface2 {
    @Override
    default int add(int x,int y) {
        return Interface1.super.add(x,y);
    }
}
