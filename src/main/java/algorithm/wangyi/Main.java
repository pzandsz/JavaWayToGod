package algorithm.wangyi;

import java.util.Scanner;

/**
 * 类说明:小易有一个古老的游戏机，上面有着经典的游戏俄罗斯方块。
 * 因为它比较古老，所以规则和一般的俄罗斯方块不同。
 * 荧幕上一共有 n 列，每次都会有一个 1 x 1 的方块随机落下，在同一列中，
 * 后落下的方块会叠在先前的方块之上，当一整行方块都被占满时，这一行会被消去，并得到1分。
 *
 * @author 曾鹏
 */
public class Main {
    public static void main(String[] args){
        Scanner in=new Scanner(System.in);
        int n=0,m=0;
        n=in.nextInt();
        m=in.nextInt();

        int[] data=new int[m];
        int[] dataN=new int[n];
        for(int i=0;i<n;i++){
            dataN[i]=0;
        }
        int next;
        for(int i=0;i<m;i++){
            next=in.nextInt();
            dataN[next-1]+=1;
        }
        int min=Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            if(dataN[i]<min){
                min=dataN[i];
            }
        }

        System.out.println(min);
    }
}
