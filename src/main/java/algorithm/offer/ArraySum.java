package algorithm.offer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;

/**
 * 类说明:数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 * 例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
 * 由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
 *
 * @author 曾鹏
 */
public class ArraySum {

    public int MoreThanHalfNum_Solution(int [] array) {
        int len=array.length;
        if(len==0){
            return 0;
        }
        if(len==1){
            return array[0];
        }

        TreeMap<Integer,Integer> map=new TreeMap<Integer,Integer>();
        //获得相同数的
        for(int i=0;i<len;i++){
            if(map.get(array[i])==null){
                map.put(array[i],1);
            }else{
                map.put(array[i],map.get(array[i])+1);
            }
        }

        int max=0,maxIndex=0;
        System.out.println(map);

        Set<Integer> integers = map.keySet();
        for(Integer i:integers){
            if(max<map.get(i)){
                max=map.get(i);
                maxIndex=i;
            }
        }



        System.out.println(max+","+len/2);
        if(max>len/2){
            return maxIndex;
        }

        return 0;

    }


    /**
     * 排序
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        //这是一个典型的排序算法 冒泡
        int temp=0;
        int len=input.length;
        for(int i=0;i<len;i++){
            for(int j=i+1;j<len;j++){
                if(input[j]<input[i]){
                    temp=input[i];
                    input[i]=input[j];
                    input[j]=temp;
                }
            }
        }


        ArrayList<Integer> answer=new ArrayList<Integer>();
        for(int i=0;i<k;i++){
            answer.add(input[i]);
        }

        return answer;

    }


    public static void main(String[] args) {
        ArraySum sum=new ArraySum();
        int[] temp={1,2,3,2,2,2,6,4,2};
        System.out.println(sum.MoreThanHalfNum_Solution(temp));
        sum.sort(temp);
    }


    public void sort(int [] input){

        ArrayList<Integer> arrayList=new ArrayList<>();

        Arrays.sort(input);

        System.out.println("----------");
        for (int i=0;i<input.length;i++){
            System.out.print(input[i]);
        }
    }
}
