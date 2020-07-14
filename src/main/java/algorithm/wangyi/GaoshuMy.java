package algorithm.wangyi;

import java.util.Scanner;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class GaoshuMy {
    public static void main(String[] args){
        Scanner in=new Scanner(System.in);
        int n=0,k=0;
        n=in.nextInt();
        k=in.nextInt();

        //分数
        int[] xingqu=new int[n];
        for(int i=0;i<n;i++){
            xingqu[i]=in.nextInt();
        }

        //状态
        int[] status=new int[n];
        for(int i=0;i<n;i++){
            status[i]=in.nextInt();
        }

        int max=0;
        int temp=0;
        for(int i=0;i<n;i++){
            if(status[i]==1){
                temp+=xingqu[i];
            }else{
                int j=0;
                int tmpMax=0;
                while(j<k&&(i+j)<n){
                    if(status[i+j]==0){
                        tmpMax+=xingqu[i+j];
                    }
                    j++;
                }
                if(tmpMax>max){
                    max=tmpMax;
                }
            }
        }

        System.out.println(max+temp);
    }
}
