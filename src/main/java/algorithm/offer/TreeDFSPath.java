package algorithm.offer;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 类说明:
 * 输入一颗二叉树的跟节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
 * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
 * @author 曾鹏
 */
public class TreeDFSPath {
    private Stack<TreeNode> stack=new Stack<>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {

        ArrayList<ArrayList<Integer>> pathLists=new ArrayList<ArrayList<Integer>>();

        if(root==null){
            return null;
        }

        //将根节点入栈
        stack.push(root);
        ArrayList<Integer> path=new ArrayList<>();

        /**
         * 判断当前节点是否为只有根节点
         */
        if(root.left==null&&root.right==null){
            //如果是叶子结点，则将该节点从栈中弹出
            path.add(stack.pop().val);

            if(path.get(0)==target){
                pathLists.add(path);
            }
            return pathLists;
        }


        //如果存在右子树或者右子树，则将当前节点加入路径
        path.add(stack.peek().val);
        FindPath2(root.left,target,path,pathLists);
        FindPath2(root.right,target,path,pathLists);

        return  pathLists;
    }


    public ArrayList<ArrayList<Integer>> FindPath2(TreeNode root, int target,
                                                  ArrayList<Integer> path, ArrayList<ArrayList<Integer>> pathLists) {


        if(root==null){
            return pathLists;
        }

        //将根节点入栈
        stack.push(root);
        ArrayList<Integer> pathNew=new ArrayList<>();
        pathNew.addAll(path);

        if(root.left==null&&root.right==null){
            pathNew.add(stack.pop().val);
            int temp=0;
            for(int i=0;i<pathNew.size();i++){
                temp+=pathNew.get(i);
            }
            if(temp==target){
                pathLists.add(pathNew);
            }
            return pathLists;
        }

        pathNew.add(stack.peek().val);
        FindPath2(root.left,target,pathNew,pathLists);
        FindPath2(root.right,target,pathNew,pathLists);

        return  pathLists;
    }

    public static void main(String[] args) {
        TreeNode root=new TreeNode(10);
        TreeNode left=new TreeNode(5);
        TreeNode leftRight=new TreeNode(4);
        TreeNode leftLeft=new TreeNode(7);
        TreeNode right=new TreeNode(12);

        left.left=leftLeft;
        left.right=leftRight;

        root.left=left;
        root.right=right;

        TreeDFSPath path=new TreeDFSPath();
        ArrayList<ArrayList<Integer>> arrayLists = path.FindPath(root, 22);

        System.out.println(arrayLists);


    }
}
