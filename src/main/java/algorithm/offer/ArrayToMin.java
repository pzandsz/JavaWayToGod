package algorithm.offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 类说明:输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
 *
 * 3+""+32和32+""+3比较，取较大值===>字符串的比较
 *
 * @author 曾鹏
 */
public class ArrayToMin {

    public String PrintMinNumber(int [] numbers) {

        int len=numbers.length;
        ArrayList<Integer> temp=new ArrayList<>(len);
        for(int i=0;i<len;i++){
            temp.add(numbers[i]);
        }


        Collections.sort(temp, (Comparator<Integer>) (o1, o2) -> {
            String str1=o1+""+o2;
            String str2=o2+""+o1;
            return str1.compareTo(str2);
        });

        String str="";
        for(int i=0;i<len;i++){
            str+=temp.get(i);
        }
        return str;
    }


    public String PrintMinNumber2(int [] numbers) {
        int n;
        String s = "";
        ArrayList<Integer> list = new ArrayList<Integer>();
        n = numbers.length;
        for (int i = 0; i < n; i++) {
            list.add(numbers[i]);

        }
        Collections.sort(list, new Comparator<Integer>() {

            @Override
            public int compare(Integer str1, Integer str2) {
                String s1 = str1 + "" + str2;
                String s2 = str2 + "" + str1;
                return s1.compareTo(s2);
            }
        });
        for(int j:list){
            s+=j;
        }
        return s;

    }
}
