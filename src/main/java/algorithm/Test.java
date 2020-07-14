package algorithm;
import java.util.*;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class Test {


//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int group = in.nextInt();
//
//        for(int i=0;i<group;i++){
//            int n = in.nextInt();
//
//            HashSet<Integer> set = new HashSet<>();
//            ArrayList<Integer[]> arrayList = new ArrayList<>();
//            while(set.size() < n){
//
//                Integer[] list = new Integer[2];
//                list[0] = in.nextInt();
//                list[1] = in.nextInt();
//                set.add(list[0]);
//
//
//                arrayList.add(list);
//            }
//            System.out.println("size"+arrayList.size());
//            if(arrayList.size()%2 == 0 && n%2 == 1){
//                System.out.println("niuniu");
//                continue;
//            }
//
//
//            if(arrayList.size()%2 == 1 && n%2 == 0){
//                System.out.println("niumei");
//                continue;
//            }
//
//            System.out.println("niuniu");
//        }
//
//
//
//    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        HashMap<Integer,Integer> map = new HashMap<>();
        int sum=0;
        int other=0;
        for(int i=0;i<n;i++){
            int key = in.nextInt();
            int value = in.nextInt();
            if(key > m ){
                sum += value;
            }else {
                int tmp = key*value;
                if(tmp >=m) {
                    sum += 1;
                }else {
                    other += key*value;
                }
            }
            map.put(key,value);
        }

        System.out.println(other/m+sum);

    }


//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        int k = in.nextInt();
//        HashMap<Integer,Integer> map = new HashMap<>();
//        for(int i=0;i<n;i++){
//            int tmp = in.nextInt();
//            Integer integer = map.get(tmp);
//            if(integer == null){
//                map.put(tmp,1);
//                continue;
//            }
//            map.put(tmp,integer+1);
//        }
//
//        int sum=0;
//        Collection<Integer> values = map.values();
//        for (Integer integer:values) {
//            sum += (integer+1)/2;
//        }
//
//        System.out.println(sum);
//    }
}
