package algorithm.offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 类说明:广度优先遍历之字形打印
 *
 * @author 曾鹏
 */
public class BFSzhi {

    private LinkedList<TreeNode> queue=new LinkedList<>();

    private ArrayList<ArrayList<Integer>> answer=new ArrayList<>();

    public ArrayList<ArrayList<Integer>> print(TreeNode root){


        if(root==null){
            return answer;
        }

        //根节点放入输出列表中
        ArrayList<Integer> temp=new ArrayList<>();
        temp.add(root.val);

        answer.add(temp);
        //将root的左右孩子放入队列中
        if(root.left!=null){

            queue.addLast(root.left);
        }
        if(root.right!=null){

            queue.addLast(root.right);
        }

        //当队列非空时，循环操作
        while (!queue.isEmpty()){
            //怎么判断在同一层中
            putInList();
        }


        return  answer;
    }

    /**
     * 将root的孩子节点全部放入输出结果中
     *
     */
    public void putInList(){

        if(queue.isEmpty()){
            return;
        }

        int size=queue.size();

        ArrayList<Integer> temp=new ArrayList<>(size);
        //将队列中的节点输出到list中
        for (int i=0;i<size;i++){
            TreeNode head = queue.removeFirst();

            temp.add(head.val);

            //添加左右孩子节点
            if(head.left!=null){

                queue.addLast(head.left);
            }
            if(head.right!=null){

                queue.addLast(head.right);
            }
        }


        //需要添加的是偶数行，应该反向打印
        if(answer.size()%2==1){
            Collections.reverse(temp);
        }
        answer.add(temp);
    }



    public static boolean checkDifferent(String initString) {
        // write code here
        int[] temp=new int[256];
        int index=0;
        for(int i=0;i<initString.length();i++){
            index=initString.charAt(i);
            if(temp[index]!=0){
                return false;
            }else{
                temp[index]=1;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(BFSzhi.checkDifferent("acbdbesy"));
    }
}
