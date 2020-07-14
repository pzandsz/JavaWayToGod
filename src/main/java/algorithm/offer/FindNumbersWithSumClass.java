package algorithm.offer;

import java.util.ArrayList;

/**
 * 类说明:输入一个递增排序的数组和一个数字S，
 * 在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
 *
 *
 * 解题思路:设置两个指针，一个指向首部head，一个指向尾部tail
 * 判断head和tail指向的值之和：
 *      如果大于sum,tail--;
 *      如果小于sum,head++;
 *
 * 关于出现多组数字和为s的情况,有这样一个结论,第一个碰到的和为sum的就是乘积最小组合
 * 数学证明:
 * 作者：马客(Mark)
 * 链接：https://www.nowcoder.com/questionTerminal/390da4f7a00f44bea7c2f3d19491311b?f=discussion
 * 来源：牛客网
 *
 * 找到的第一组（相差最大的）就是乘积最小的。
 * 可以这样证明：
 *      考虑x+y=C（C是常数），x*y的大小。
 *      不妨设y>=x，y-x=d(d>=0)，
 *      即y=x+d, 2x+d=C, x=(C-d)/2,
 *      x*y=x(x+d)=(C-d)(C+d)/4=(C^2-d^2)/4，
 *      也就是x*y是一个关于变量d的二次函数，对称轴是y轴，开口向下。d是>=0的，d越大, x*y也就越小。
 * @author 曾鹏
 */
public class FindNumbersWithSumClass {
    public ArrayList<Integer> FindNumbersWithSum(int [] array, int sum) {
        int len=array.length;
        int head=0;
        int tail=len-1;



        ArrayList<Integer> answer=new ArrayList<Integer>();

        if(len==0){
            return answer;
        }
        while(true){
            if(array[head]+array[tail]==sum){
                answer.add(array[head]);
                answer.add(array[tail]);
                break;
            }

            if(array[head]+array[tail]<sum){
                head++;
            }
            if(array[head]+array[tail]>sum){
                tail--;
            }

            if(head>tail){
                break;
            }
        }

        return answer;
    }
}
