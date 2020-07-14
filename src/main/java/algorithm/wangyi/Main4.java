package algorithm.wangyi;

import java.util.Scanner;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class Main4 {
    public static void main(String[] args){
        Scanner in=new Scanner(System.in);
        int a=0,b=0,c=0;
        a=in.nextInt();
        b=in.nextInt();
        c=in.nextInt();

        int max=0;
        int temp1=a*b*c;
        max=temp1>max?temp1:max;

        int temp2=(a+b)*c;
        max=temp2>max?temp1:max;

        int temp3=a*(b+c);
        max=temp3>max?temp1:max;

        int temp4=(a+c)*b;
        max=temp4>max?temp1:max;

        int temp5=(a+c)+b;
        max=temp5>max?temp1:max;

        System.out.println(max);

    }
}
