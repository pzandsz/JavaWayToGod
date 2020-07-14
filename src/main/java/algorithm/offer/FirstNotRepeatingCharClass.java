package algorithm.offer;

import java.util.HashMap;

/**
 * 类说明:在一个字符串(0<=字符串长度<=10000，
 * 全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.
 *
 * 借助Map结构的特性解决问题
 * @author 曾鹏
 */
public class FirstNotRepeatingCharClass {
    public int FirstNotRepeatingChar(String str){
        int len=str.length();
        HashMap<Character,Integer> map=new HashMap<>();
        for(int i=0;i<len;i++){
            Character temp=str.charAt(i);
            if(map.get(temp)==null){
                //未出现过
                map.put(temp,1);
            }else {
                //表示出现过的次数大于1次
                map.put(temp,2);
            }
        }

        for(int i=0;i<len;i++){
            if(map.get(str.charAt(i)).intValue()==1){
                return i;
            }
        }
        return -1;
    }
}
