package algorithm.wangyi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 类说明:小易觉得高数课太无聊了，决定睡觉。
 * 不过他对课上的一些内容挺感兴趣，所以希望你在老师讲到有趣的部分的时候叫醒他一下。
 * 你知道了小易对一堂课每分钟知识点的感兴趣程度，并以分数量化，以及他在这堂课上每分钟是否会睡着，
 * 你可以叫醒他一次，这会使得他在接下来的k分钟内保持清醒。你需要选择一种方案最大化小易这堂课听到的知识点分值。
 *
 * @author 曾鹏
 */
public class GaoShu {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line1 = br.readLine().split(" ");
        String[] line2 = br.readLine().split(" ");
        String[] line3 = br.readLine().split(" ");
        int n = Integer.parseInt(line1[0]);
        int k = Integer.parseInt(line1[1]);
        int[] score = new int[n];
        int[] sleep = new int[n];
        int max1 = 0;
        int win = 0;
        for (int i = 0; i < n; i++) {
            score[i] = Integer.parseInt(line2[i]);
            if (Integer.parseInt(line3[i]) == 1) {
                max1 += score[i];
                score[i] = 0;
            }
            if (i < k) {
                win += score[i];
            }
        }
        int max2 = win;
        for (int i = k; i < n; i++) {
            win -= score[i - k];
            win += score[i];
            if (win > max2) {
                max2 = win;
            }
        }
        System.out.println(max1 + max2);
    }
}
