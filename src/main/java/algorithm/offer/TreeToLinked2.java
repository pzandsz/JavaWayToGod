package algorithm.offer;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class TreeToLinked2 {
    public TreeNode Convert(TreeNode root)
    {

        if(root==null)return null;
        if(root.left==null&&root.right==null)return root;
        TreeNode left=Convert(root.left);
        TreeNode p=left;
        while(p!=null&&p.right!=null)
        {
            p=p.right;
        }
        if(left!=null)
        {
            p.right=root;
            root.left=p;
        }
        TreeNode right=Convert(root.right);
        if(right!=null)
        {
            root.right=right;
            right.left=root;
        }

        return  left!=null?left:root;
    }
}
