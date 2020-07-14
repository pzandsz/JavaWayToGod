import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * 类说明:
 *
 * 每个输入包含一个测试用例。
 * 每个测试用例的第一行包含两个正整数，分别表示工作的数量N(N<=100000)和小伙伴的数量M(M<=100000)。
 * 接下来的N行每行包含两个正整数，分别表示该项工作的难度Di(Di<=1000000000)和报酬Pi(Pi<=1000000000)。
 * 接下来的一行包含M个正整数，分别表示M个小伙伴的能力值Ai(Ai<=1000000000)。
 * 保证不存在两项工作的报酬相同。
 *
 * @author 曾鹏
 */
public class NiuNiuFindWork2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        //用于存放工作难度和工作报酬
        int[][] arr = new int[N][2];
        //创建TreeMap对象,用于
        TreeMap map = new TreeMap();

        //初始化工作难度和报酬
        for(int i = 0; i < N; i++) {
            arr[i][0] = sc.nextInt();
            arr[i][1] = sc.nextInt();
        }
        //排序,自定义比较方法
        Arrays.sort(arr, (e1, e2) -> (int)(e1[0] - e2[0]));
        for(int i = 1; i < arr.length; i++) {
            arr[i][1] = Math.max(arr[i-1][1], arr[i][1]);
        }

        //将arr数组放入TreeMap对象
        for(int i = 0; i < arr.length; i++) {
            map.put(arr[i][0], arr[i][1]);
        }
        for(int i = 0; i < M; i++) {
            int ability = sc.nextInt();
            /**
             * floorKey()方法的作用是:用于返回的最大键小于或等于给定的键
             */
            Integer index = (Integer) map.floorKey(ability);
            if(index != null) {
                System.out.println(map.get(index));
            } else {
                System.out.println(0);
            }
        }
    }
}
