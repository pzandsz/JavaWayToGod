package algorithm.offer;

import com.sun.deploy.util.StringUtils;

/**
 * 类说明:输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
 *
 * @author 曾鹏
 */
public class Eleven {

    public static void main(String[] args) {
        int i=-1;
        String s = Integer.toBinaryString(i);
        int num=0;
        for (int j = 0; j < s.length(); j++) {
            if(s.charAt(j)=='1'){
                num++;
            }

        }
        System.out.println(num);
        System.out.println(Math.pow(1.32,5));

    }
}
