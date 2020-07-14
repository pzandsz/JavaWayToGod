package algorithm.offer;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class TestReference {

    public static void change(TreeNode root){
        TreeNode temo=root;
        temo.left=new TreeNode(55);
    }

    public static void main(String[] args) {
        TreeNode root=new TreeNode(5);
        TreeNode left=new TreeNode(5);

        TreeNode right=new TreeNode(5);
        root.right=right;
        root.left=left;

        System.out.println( root.left.val);
        change(root);
        System.out.println( root.left.val);



    }
}
