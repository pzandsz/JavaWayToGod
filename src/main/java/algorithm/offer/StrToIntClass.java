package algorithm.offer;

/**
 * 类说明:将一个字符串转换成一个整数(实现Integer.valueOf(string)的功能，
 * 但是string不符合数字要求时返回0)，
 * 要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0。
 *
 * @author 曾鹏
 */
public class StrToIntClass {
    public int StrToInt(String str) {
        if(str.length()==0){
            return 0;
        }

        int answer=0;
        boolean isFushu=false;
        for(int i=0;i<str.length();i++){



            char temp = str.charAt(i);
            if(i==0&&str.charAt(0)=='+'){
                continue;
            }
            if(i==0&&str.charAt(0)=='-'){
                isFushu=true;
                continue;
            }
            if(isInteger(temp)){
                answer=answer*10+(temp-'0');
            }else{
                return 0;
            }
        }

        if(isFushu){
            answer=answer*-1;
        }

        return answer;
    }

    public boolean isInteger(char a){
        if(a>='0'&&a<='9'){
            return true;
        }else{
            return false;
        }

    }
}
