package algorithm.offer;

/**
 * 类说明:输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
 * 要求不能创建任何新的结点，只能调整树中结点指针的指向。
 *
 * @author 曾鹏
 */
public class TreeToLinked {

    public TreeNode Convert(TreeNode pRootOfTree) {

        if(pRootOfTree==null){
            return null;
        }

        //处理左子树

        //处理右子树

        return pRootOfTree;
    }

    /**
     * 将left指向pre,right执行next
     * 处理左子树
     * @param oldRoot
     * @param leftRoot
     */
    public void ConvertLeft(TreeNode oldRoot,TreeNode leftRoot) {
        if(leftRoot==null){
            return;
        }

        //判断leftRoot是否为叶子结点
        if(leftRoot.left==null&&leftRoot.right==null){

            leftRoot.left.right=oldRoot;
            return;
        }

        /**
         * 判断leftRoot的左右子树是否为叶子结点
         * 情况一:根节点，左子树(为叶子)，右子树(为叶子) 均不为空
         * 情况二:根节点，左子树(为叶子)不为空，右子树为空
         * 情况三:根节点，右子树(为叶子)不为空，左子树为空
         */
        boolean left=leftRoot.left!=null&&leftRoot.left.left==null&&leftRoot.left.right==null;
        boolean right=leftRoot.right!=null&&leftRoot.right.left==null&&leftRoot.right.right==null;

        boolean one=left&&right;
        boolean two=left&&leftRoot.right==null;
        boolean three=right&&leftRoot.left==null;


        /**
         * 情况一的处理:
         * 1.将新根的左子树的右指针指向新根
         * 2.将新根的右子树的左指针指向新根
         * 3.将新根的右子树的右指针指向旧根
         * 4.将旧跟的左指针指向新根的右子树
         */
        if(one){
            leftRoot.left.right=leftRoot;
            leftRoot.right.left=leftRoot;
            leftRoot.right.right=oldRoot;
            oldRoot.left= leftRoot.right;
            return;
        }

        /**
         * 情况二的处理:
         * 1.将新根的左子树的右指针指向新根
         * 2.将新根的右指针指向旧根
         */
        if(two){
            leftRoot.left.right=leftRoot;
            leftRoot.right=oldRoot;
            return;
        }

        /**
         * 情况三:
         *
         * 1.将新根的右子树的左指针指向新根
         * 2.将新根的右子树的右指针指向旧根
         * 3.将旧跟的左指针指向新根的右子树
         */
        if(three){
            leftRoot.right.left=leftRoot;
            leftRoot.right.right=oldRoot;
            oldRoot.left= leftRoot.right;
            return;
        }

        //处理左子树



        //处理右子树


    }

    /**
     * 处理右子树
     * @param oldRoot
     * @param leftRoot
     */
    public void ConvertRight(TreeNode oldRoot,TreeNode leftRoot) {



        //处理左子树

        //处理右子树


    }
}
