package algorithm.wangyi;

import java.util.Scanner;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class Solution1 {
    public static void main(String[] args){
        int head=0,tail=0;
        Scanner in=new Scanner(System.in);
        head=in.nextInt();
        tail=in.nextInt();

        int answer=0;

        for(int i=head;i<=tail;i++){
            if(i%3==1){
                continue;
            }
            answer++;
        }

        System.out.println(answer);
    }
}
