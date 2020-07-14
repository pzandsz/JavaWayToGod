package algorithm.offer;

/**
 * 类说明:描述
 * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
 *
 * @author 曾鹏
 */
public class Sixteen {
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {



        if(root1!=null&&root2!=null&&root1.val==root2.val){

            if(root2.left==null&&root2.right==null){
                return true;
            }
            if(root2.left==null){
                return HasSubtree(root1.right,root2.right);
            }
            if(root2.right==null){
                return HasSubtree(root1.left,root2.left);
            }
            return HasSubtree(root1.left,root2.left)&&HasSubtree(root1.right,root2.right);

        }else{
            if(root1.left==null&&root1.right==null){
                return false;
            }
            if(root1.left==null){
                return HasSubtree(root1.right,root2);
            }
            if(root1.right==null){
                return HasSubtree(root1.left,root2);
            }
            return HasSubtree(root1.left,root2)||HasSubtree(root1.right,root2);
        }
    }

    public static void main(String[] args) {
        TreeNode root1=new TreeNode(8);
        root1.left=new TreeNode(8);
        root1.right=new TreeNode(7);
        root1.left.left=new TreeNode(9);
        root1.left.right=new TreeNode(2);

        root1.left.right.left=new TreeNode(4);
        root1.left.right.right=new TreeNode(7);






    }
}
