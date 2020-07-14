package algorithm.wangyi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 类说明:牛牛以前在老师那里得到了一个正整数数对(x, y), 牛牛忘记他们具体是多少了。
 *
 * 但是牛牛记得老师告诉过他x和y均不大于n, 并且x除以y的余数大于等于k。
 * 牛牛希望你能帮他计算一共有多少个可能的数对。
 *
 * @author 曾鹏
 */
public class Main2 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
        String[] s=bf.readLine().split(" ");
        int n=Integer.parseInt(s[0]);
        int k=Integer.parseInt(s[1]);
        long r=getResult(n,k);
        System.out.println(r);
    }

    public static long getResult(int n,int k){
        if(k==0) {
            return (long)n*n;
        }

        long count=0;
        for(int i=k+1;i<=n;i++){
            int hang=i-k;

            //[n/y]个循环节
            int x=n/i;
            //
            int y=n%i;
            count+=hang*x;
            //当k=0的时候由于余数0出现在循环的末尾
            if(y>=k){
                count+=y-k+1;
            }
            System.out.println(count);
        }

        return count;
    }

}
