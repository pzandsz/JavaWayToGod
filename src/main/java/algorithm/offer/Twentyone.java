package algorithm.offer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 类说明:二叉树的广度优先遍历
 *
 * @author 曾鹏
 */
public class Twentyone {

    List<TreeNode> treeNodeQueue=new LinkedList<>();

    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> list=new ArrayList<Integer>();

        if(root==null){
            return list;
        }

        addToQueue(root);
        addToQueue(root.left);
        addToQueue(root.right);

        root=root.left;

        return list;
    }

    public void addToQueue(TreeNode node){
        if(node==null){
            return;
        }
        treeNodeQueue.add(node);

    }
}
