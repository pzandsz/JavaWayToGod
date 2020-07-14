package algorithm.wangyi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 类说明:小易在学校中学习了关于字符串的理论, 于是他基于此完成了一个字典的项目。
 *
 * 小易的这个字典很奇特, 字典内的每个单词都包含n个'a'和m个'z', 并且所有单词按照字典序排列。
 *
 * 小易现在希望你能帮他找出第k个单词是什么。
 *
 * @author 曾鹏
 */
public class ZiDian {
    private static int n;
    private static int m;
    private static int k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        n = Integer.parseInt(line[0]);
        m = Integer.parseInt(line[1]);
        k = Integer.parseInt(line[2]);
        StringBuilder sb = new StringBuilder();
        while (n != 0 && m != 0) {
            long count = 1;
            for (int i = 0; i < n - 1; i++) {
                count *= (n - 1) + m - i;
                count /= (i + 1);
                if (count > k) {
                    break;
                }
            }
            if (count >= k) {
                sb.append('a');
                n--;
            } else {
                sb.append('z');
                m--;
                k -= count;
            }
        }
        if (k != 1) {
            System.out.println(-1);
            return;
        }
        while (n-- > 0) {
            sb.append('a');
        }
        while (m-- > 0) {
            sb.append('z');
        }
        System.out.println(sb.toString());
    }
}
