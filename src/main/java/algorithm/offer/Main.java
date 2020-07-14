package algorithm.offer;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int n = in.nextInt();
            if(n<3||n>20){
                continue;
            }
            int[] height=new int[n];
            for(int i=0;i<n;i++){
                height[i]=in.nextInt();
            }


            for(int i=0;i<n;i++){
                for(int j=1;j<n;j++){
                    if(height[j]<height[j-1]){
                        System.out.println(height[j]);
                        int temp=height[j];
                        height[j]=height[j-1];
                        height[j-1]=temp;
                    }
                }
            }
            System.out.println();
            for(int j=0;j<n;j++){
                System.out.print(height[j]+" ");
            }

            int max1=0;
            for(int i=0;i<n-2;i++){
                if(height[i+2]-height[i]>max1){
                    max1=height[i+2]-height[i];
                }
            }
            System.out.println(max1);

        }
    }
}
