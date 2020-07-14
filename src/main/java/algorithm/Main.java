package algorithm;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t=in.nextInt();
        for(int i=0;i<t;i++){
            String[] str=new String[2];
            str[0]=in.next();
            str[1]=in.next();

            //对第一个字符串进行拆分

           if(str[1].contains(str[0])){
               if(str[1].length()==str[0].length()){
                   System.out.println(false);
                   return;
               }
               System.out.println(true);
           }
            if(str[0].contains(str[1])){
                System.out.println(false);
            }


            String[] split1 = str[0].split("[.]");

            String[] split2 = str[1].split("[.]");


            //对第一个字符串数组进行循环查询
            for(int j=0;j<split1.length;j++){
                //判断j是否大于split2的长度
                if(j>split2.length-1){
                    //默认补0
                    if(split1[j].charAt(0)>'0'){
                        System.out.println(false);
                        return;
                    }
                }else {
                    if(split1[j].charAt(0)>split2[j].charAt(0)){
                        System.out.println(false);
                        return;
                    }else if(split1[j].charAt(0)<split2[j].charAt(0)){
                        System.out.println(true);
                        return;
                    }
                }
            }
            System.out.println(false);
        }
    }
}