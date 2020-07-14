package algorithm.wangyi;


import java.util.Scanner;

/**
 * 类说明:整理房间
 *
 * @author 曾鹏
 */
public class ZhengliFangjian {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] nums = new int[4][4];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 4; k++){
                    nums[j][k] = sc.nextInt();
                }
            }
            int result = findMinMoveTimes(nums);
            if (result == Integer.MAX_VALUE)
                result = -1;
            System.out.println(result);
        }
    }

    private static int findMinMoveTimes(int[][] nums) {
        int i = 0;
        int j = 0;
        int k = 0;
        int z = 0;
        int minTimes = Integer.MAX_VALUE;
        boolean flag = false;
        for (i = 0; i < 4; i++){
            if (flag){
                rotate(nums, 0);
            }
            for (j = 0; j < 4; j++){
                if (flag){
                    rotate(nums, 1);
                }
                for (k = 0; k < 4; k++){
                    if (flag){
                        rotate(nums, 2);
                    }
                    for (z = 0; z < 4; z++){
                        if (flag){
                            rotate(nums, 3);
                        }else {
                            flag = true;
                        }
                        if (isSquare(nums[0], nums[1], nums[2], nums[3])){
                            int times = i + j + k + z;
                            if (times < minTimes){
                                minTimes = times;
                            }
                        }
                    }

                }
            }
        }
        return minTimes;
    }

    private static void rotate(int[][] nums, int index) {
        int[] num = nums[index];
        int oldx = num[0];
        int oldy = num[1];
        int centerx = num[2];
        int centery = num[3];
        nums[index][0] = centerx + centery - oldy;
        nums[index][1] = centery - centerx + oldx;
    }

    private static boolean isSquare(int[] neePoint1, int[] neePoint2, int[] neePoint3, int[] neePoint4){
        long[] dis = new long[6];
        dis[0] = dis(neePoint1, neePoint2);
        dis[1] = dis(neePoint1, neePoint3);
        dis[2] = dis(neePoint1, neePoint4);
        dis[3] = dis(neePoint2, neePoint3);
        dis[4] = dis(neePoint2, neePoint4);
        dis[5] = dis(neePoint3, neePoint4);
        int count = 0;
        long minDis = Long.MAX_VALUE;
        for (int i = 0; i < 6; i++){
            if (dis[i] < minDis){
                minDis = dis[i];
            }
        }
        for (int i = 0; i < 6; i++){
            if (dis[i] == minDis){
                count++;
            }
        }
        return count == 4;
    }

    private static long dis(int[] neePoint1, int[] neePoint2) {
        long x = neePoint1[0] - neePoint2[0];
        long y = neePoint1[1] - neePoint2[1];
        return (x * x + y * y);
    }

}
