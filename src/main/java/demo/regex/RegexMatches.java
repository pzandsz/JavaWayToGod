package demo.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试正则表达式匹配
 * @author 曾鹏
 */
public class RegexMatches {
    public static void main(String[] args) {

        //测试电话号码匹配
        System.out.println("电话号码是否匹配: "+phoneMatcher("124856455"));
        System.out.println("电话号码是否匹配: "+phoneMatcher("13879484330"));
        System.out.println("qq号是否匹配: "+qqMatcher("1330"));
        System.out.println("qq号是否匹配: "+qqMatcher("138484330"));
        System.out.println("字符串是否匹配: "+strMatcher("1hgduiafSFF30"));
        System.out.println("字符串是否匹配: "+strMatcher("WelcomeToChina"));

    }

    /**
     * 电话号码匹配
     * @param phone
     * @return
     */
    public static boolean phoneMatcher(String phone){
        //规则：号码第一位是1，第二位是3,5,7,8中的一个，后面跟着9个数字
        String refex="[1][3578]\\d{9}";
        return phone.matches(refex);
    }

    /**
     * 匹配qq号码
     * @param qq
     * @return
     */
    public static boolean qqMatcher(String qq){
        //规则：首字母不能为0，qq号码长度为5到10
        String regex="[1-9]\\d{5,10}";
        return qq.matches(regex);
    }

    /**
     * 匹配字符串
     * @param str
     * @return
     */
    public static boolean strMatcher(String str){
        //规则：以W为开头的任何字符串
        String regex="W.*";
        return str.matches(regex);
    }

    /**
     * 替换字符串
     * @param str
     */
    public static void replaceFirst(String str){

    }
}
