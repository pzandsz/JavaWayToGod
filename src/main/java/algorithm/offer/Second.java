package algorithm.offer;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class Second{
    public static String replaceSpace(StringBuffer str) {
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==' '){
                str.replace(i,i+1,"%20");
            }
        }



        return str.toString();
    }

    public static void main(String[] args) {

        System.out.println(Second.replaceSpace(new StringBuffer("We Are Happy")));
    }
}
