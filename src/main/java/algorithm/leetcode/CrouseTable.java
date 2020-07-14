package algorithm.leetcode;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 类说明:课程表
 * 1.将二维矩阵转换为图
 * 2.对图进行深度优先搜索
 * 3.若出现死循环，则存在环
 *
 * @author 曾鹏
 */
public class CrouseTable {


    public static void main(String[] args) {
        int[][] data = new int[5][2];
        int[] d = {1,2};
        int[] d1= {1,3};
        int[] d2= {2,3};
        int[] d3= {3,5};
        int[] d4= {3,13};

        data[0] = d;
        data[1] = d1;
        data[2] = d2;
        data[3] = d3;
        data[4] = d4;


        canFinish(2,data);
    }

    public static HashMap<Integer,Node> mapping = new HashMap<Integer, Node>();
    public static int numCoursesAll;
    public static boolean canFinish(int numCourses, int[][] prerequisites) {

        numCoursesAll = numCourses;
        //1.将二维矩阵转换为图对象
        for(int i=0 ; i<prerequisites.length ; i++){
            if(mapping.containsKey(prerequisites[i][0])){
                mapping.get(prerequisites[i][0]).nextList.add(prerequisites[i][1]);
                continue;
            }

            Node node = new Node(prerequisites[i][0],new ArrayList());
            node.nextList.add(prerequisites[i][1]);
            mapping.put(node.val,node);

        }

        Gson gson = new Gson();
        System.out.println(gson.toJson(mapping));

        return false;
    }




    static class Node{
        public int val;
        public ArrayList<Integer> nextList;

        public Node(){

        }

        public Node(int _val){
            this.val = _val;
        }

        public Node(int _val , ArrayList _nextList ){
            this.val = _val;
            this.nextList = _nextList;
        }
    }

}
