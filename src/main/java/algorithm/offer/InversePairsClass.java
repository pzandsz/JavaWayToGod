package algorithm.offer;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 类说明:逆序对问题
 *
 * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
 * 输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007
 * @author 曾鹏
 */
public class InversePairsClass {
    /**
     * 暴力求解,只能通过50%的用例
     * @param array
     * @return
     */
    public static int InversePairs(int [] array) {

        int answer=0;
        int len=array.length;
        int temp=0;
        for(int i=0;i<len;i++){
            for(int j=i+1;j<len;j++){
                if(array[j]<array[i]){
                    answer++;
                    if(answer>1000000007){
                        answer= answer%1000000007;
                    }
                }
            }
        }

        System.out.println(answer);
        return answer;
    }

    /**
     * 第二种解题思路是通过归并排序的思想
     * @param array
     * @return
     */
    public static int InversePairs2(int [] array) {

        int answer=0;
        int len=array.length;
        int temp=0;

        //对array进行排序
        Arrays.sort(array);
        //创建一个HashMap
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<len;i++){

        }

        System.out.println(answer);
        return answer;
    }

    public static void main(String[] args) {
        int[] a={364,637,341,406,747,995,234,971,571,219,
                 993,407,416,366,315,301,601,650,418,355,
                 460,505,360,965,516,648,727,667,465,849,
                 455,181,486,149,588,233,144,174,557,67,
                 746,550,474,162,268,142,463,221,882,576,
                 604,739,288,569,256,936,275,401,497,82,
                 935,983,583,523,697,478,147,795,380,973,
                 958,115,773,870,259,655,446,863,735,784,
                 3,671,433,630,425,930,64,266,235,187,284,
                 665,874,80,45,848,38,811,267,575};
        InversePairs(a);
    }
}
