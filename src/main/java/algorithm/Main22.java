package algorithm;

import java.util.Scanner;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class Main22 {
    public static int N=0;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(Integer.MAX_VALUE+1);
//        int t=in.nextInt();
//        for(int i=0;i<t;i++){
//            int n=in.nextInt();
//            N=n;
//            while(!count(N)){
//                if(N>Integer.MAX_VALUE){
//                    System.out.println(false);
//                    break;
//                }
//            }
//            System.out.println(true);
//        }
    }

    public static boolean count(int n){
        boolean answer=false;
        int sum=0;
        while (n>0){
            sum+=(n%10)*(n%10);
            n=n/10;
        }

        N=sum;
        return  sum==1;
    }
}
