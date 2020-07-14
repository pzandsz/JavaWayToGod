package algorithm.offer;

import java.util.Scanner;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class NiuNiuFindWork {
    public static void main(String[] args){
        int N,M;
        Scanner in=new Scanner(System.in);
        while(in.hasNext()){
            N=in.nextInt();
            M=in.nextInt();

            //输入工作的难度报酬  第一列表示难度 第二列表示报酬
            int[][] P=new int[N][2];
            int temp=0,k=0,tempValue=0;
            for(int i=0;i<N;i++){
                //当i=0时，直接插入
                if(i==0){
                    P[i][0]=in.nextInt();
                    P[i][1]=in.nextInt();
                    continue;
                }
                k=i-1;
                temp=in.nextInt();
//                System.out.print("\n-->s"+temp);
                tempValue=in.nextInt();
//                System.out.println("-->s"+tempValue);
                //找到合适的位置就插入

                P[k+1][0]=temp;
                P[k+1][1]=tempValue;
                while(temp<P[k][0]){
                    //小于前一个位置的值时


                    //向后挪一个位置
                    P[k+1][0]=P[k][0];
                    P[k+1][1]=P[k][1];
                    P[k][0]=temp;
                    P[k][1]=tempValue;
                    k--;

                    if(k<0){
                        break;
                    }
                }

            }

            System.out.println();
            for(int i=0;i<N;i++){
                System.out.println("==========>"+P[i][0]+","+P[i][1]);
            }
            //根据难度将P排序


            //输入小伙伴们的能力值
            int[] D=new int[M];
            System.out.println("M:"+M);
            for(int i=0;i<M;i++){
                D[i]=in.nextInt();
                int max=0;
                for(int j=N-1;j>=0;j--){
                    if(P[j][0]<=D[i]){

                        if(max<P[j][1]){
                           max=P[j][1];
                           break;
                        }

                    }
                }
                System.out.println("max:"+max);
            }
//
//            for(int i=0;i<M;i++){
//                System.out.println("..."+D[i]);
//            }



        }



    }
}
