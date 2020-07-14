package algorithm.wangyi;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 类说明:塔
 *
 * @author 曾鹏
 */
public class Ta {
    public static void main(String[] args)
    {
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        scanner.nextLine();
        while(n-->0)
        {
            int[][][]data=new int [4][4][2];
            for(int i=0;i<4;i++)
            {
                data[i]=getXY(scanner.nextLine());
            }

            int sum=13;
            for(int a=0;a<4;a++)
            {
                int x1=data[0][a][0],y1=data[0][a][1];
                for(int b=0;b<4;b++)
                {
                    int x2=data[1][b][0],y2=data[1][b][1];
                    for(int c=0;c<4;c++)
                    {
                        int x3=data[2][c][0],y3=data[2][c][1];
                        for(int d=0;d<4;d++)
                        {
                            int x4=data[3][d][0],y4=data[3][d][1];
                            if(checkQ(x1,y1,x2,y2,x3,y3,x4,y4))
                            {
                                if(a+b+c+d<sum)
                                    sum=a+b+c+d;
                            }
                        }
                    }
                }
            }
            if(sum==13)
                sum=-1;
            System.out.println(sum);
        }
    }

    private static boolean checkQ(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4)
    {
        int x[]=new int[4];
        int y[]=new int[4];
        x[0]=x1;x[1]=x2;x[2]=x3;x[3]=x4;
        y[0]=y1;y[1]=y2;y[2]=y3;y[3]=y4;
        Arrays.sort(x);
        Arrays.sort(y);
        if(x[0]==x[1]&&x[2]==x[3])
        {
            if(y[0]==y[1]&&y[2]==y[3])
            {
                if(x[1]!=x[2]&&y[1]!=y[2])
                {
                    if(x[3]-x[0]==y[3]-y[0])
                        return true;
                }
            }
        }
        return false;
    }

    private static int[][] getXY(String nextLine) {
        String s[]=nextLine.split(" ");
        int x=Integer.parseInt(s[0]);
        int y=Integer.parseInt(s[1]);
        int a=Integer.parseInt(s[2]);
        int b=Integer.parseInt(s[3]);

        int data[][]=new int[4][2];
        data[0][0]=x;
        data[0][1]=y;
        data[1][0]=a+b-y;
        data[1][1]=b-a+x;
        data[2][0]=a+a-x;
        data[2][1]=b+b-y;
        data[3][0]=a-b+y;
        data[3][1]=a+b-x;

        return data;
    }
}
