package demo.ConcurrentDemo.ForkJoin.SUM;

import java.util.Random;

/**
 * 用于创建一个长度为4000的数组
 * @author 曾鹏
 */
public class MakeArray {
    public static final int ARRAY_LENGTH=4000;
    public static final int THRESHOLD=47;

    /**
     * 创建一个数组
     */
    public static int[] makeArray(){
        //new一个随机数发生器
        Random random=new Random();
        int[] result=new int[ARRAY_LENGTH];

        for (int i = 0; i < ARRAY_LENGTH; i++) {
            //随机数填充
            result[i]=random.nextInt(ARRAY_LENGTH*3);
        }
        return  result;
    }
}
