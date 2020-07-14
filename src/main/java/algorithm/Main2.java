package algorithm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class Main2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n=0,q=0;
        n=in.nextInt();
        q=in.nextInt();

        int[] data=new int[n];
        int max=0;
        for(int i=0;i<n;i++){
            data[i]=in.nextInt();
            if(data[i]>max){
                max=data[i];
            }
        }

        int x=0;
        for(int i=0;i<q;i++){
            x=in.nextInt();
            if(x>max){
                System.out.println(0);
                continue;
            }
            int num=0;
            for(int j=0;j<n;j++){
                if(data[j]>=x){
                    num++;
                }
            }
            max--;
            System.out.println(num);
        }
    }
}
