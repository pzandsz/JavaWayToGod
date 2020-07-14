package algorithm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
import java.util.Scanner;
public class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String str1=in.nextLine();
        String str2=in.nextLine();

        System.out.println(str1);
        String[] strArray1=str1.split(" ");
        System.out.println(strArray1[0]);
        String[] strArray2=str2.split(" ");

        int j=0;
        for(int i=0;i<strArray1.length;i++){
            System.out.print(strArray1[i]+" ");

            if((i+1)%4==0){
                System.out.print(strArray2[j]+" ");
                j++;
            }
        }

        for(;j<strArray2.length;j++){
            System.out.print(strArray2[j]+" ");
        }

    }
}
