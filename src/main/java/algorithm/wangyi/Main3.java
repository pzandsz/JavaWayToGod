package algorithm.wangyi;

import java.util.Scanner;

/**
 * 类说明:
 * 又到了丰收的季节，恰逢小易去牛牛的果园里游玩。
 * 牛牛常说他对整个果园的每个地方都了如指掌，小易不太相信，所以他想考考牛牛。
 * 在果园里有N堆苹果，每堆苹果的数量为ai，小易希望知道从左往右数第x个苹果是属于哪一堆的。
 * 牛牛觉得这个问题太简单，所以希望你来替他回答。
 *
 * @author 曾鹏
 */
public class Main3 {
    public static void main(String[] args){
        Scanner in=new Scanner(System.in);
        int n=0,m=0;
        n=in.nextInt();

        //输入苹果堆数
        int[] dataN=new int[n];
        for(int i=0;i<n;i++){
            dataN[i]=in.nextInt();
        }
        //输入询问次数
        m=in.nextInt();
        int[] dataM=new int[m];
        for(int i=0;i<m;i++){
            dataM[i]=in.nextInt();
        }


        System.out.println("\nm:"+m+",n:"+n);
        for(int i=0;i<m;i++){
            int temp=0;
            for(int j=0;j<n;j++){
                temp+=dataN[j];
                if(dataM[i]<=temp){
                    System.out.println(j+1);
                    break;
                }

            }
        }
    }
}
