package algorithm.offer;

/**
 * 类说明:求出1~13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？
 *
 * 借鉴他人的代码
 * @author 曾鹏
 */
public class NumberOfOne {
    public static int NumberOf1Between1AndN_Solution(int n) {

        int count = 0;
        for (int i = 1; i <= n; i *= 10) {
            //高位n/i,低位n%i  例如3215/100=32,3215%100==15 ===>高位为32,低位为15
            int a = n / i,b = n % i;
            /**
             * 针对的是求数字1出现的次数，如果求的是数字5，就不能通过这种方式实现了
             * 之所以补8，是因为当百位为0，则a/10==(a+8)/10，
             * 当百位>=2，补8会产生进位位，效果等同于(a/10+1)
             */
            count += (a + 8) / 10 * i + ((a % 10 == 1) ? b + 1 : 0);
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(NumberOf1Between1AndN_Solution(30143));
    }


    /**
     * 自己的代码
     * @param n
     * @return
     */
    public int NumberOf1Between1AndN_SolutionMySelf(int n) {

        int count = 0;
        for (int i = 1; i <= n; i *= 10) {
            int a = n / i,b = n % i;
            if(a%10>1){
                count+=(a/10+1)*i;
            }
            if(a%10==1){
                count+=(a/10)*i+b+1;
            }
            if(a%10<1){
                count+=(a/10)*i;
            }
        }
        return count;
    }
}
