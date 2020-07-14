package algorithm;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

/**
 * 类说明:使用位图法实现去重
 *
 * @author 曾鹏
 */
public class BitMapTest {

    int ARRNUM = 800;
    int LEN_INT = 32;
    int mmax = 9999;
    int mmin = 1000;
    int N = mmax - mmin + 1;

    public void findDup_jdk(){
        System.out.println("*******调用JDK中的库方法   开始********");

        //java提供了一个BitSet类专门用于进行位运算
        BitSet bitSet=new BitSet(N);
        //返回一个长度为ARRNUM的数组
        int[] array = getArray(ARRNUM);

        for (int i = 0; i < ARRNUM; i++) {
            //为什么要减去一个mmin?
            bitSet.set(array[i] - mmin);
        }
        int count = 0;
        for (int j = 0; j < bitSet.length(); j++) {

            //bitSet.get(j)用于获得指定位置的bit值(true,false)
            //true表示该位上存在值,false表示不存在值
            if (bitSet.get(j)) {
                System.out.print(j + mmin + " ");
                count++;
            }
        }
        System.out.println();
        System.out.println("排序后的数组大小为：" + count );
        System.out.println("*******调用JDK中的库方法--结束********");

    }

    /**
     * 返回指定ARRNUM大小的数组
     * @param ARRNUM
     * @return
     */
    public int[] getArray(int ARRNUM) {

        //测试用
        int array1[] = { 1000, 1002, 1032, 1033, 6543, 9999, 1033, 1000 };

        int array[] = new int[ARRNUM];
        System.out.println("数组大小：" + ARRNUM);
        Random r = new Random();
        for (int i = 0; i < ARRNUM; i++) {
            array[i] = r.nextInt(N) + mmin;
        }

        System.out.println(Arrays.toString(array));
        return array;
    }


    public static void main(String args[]) {
        new BitMapTest().findDup_jdk();
    }

}
