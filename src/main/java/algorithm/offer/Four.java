package algorithm.offer;

/**
 * 类说明:重建二叉树
 *输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
 * @author 曾鹏
 */
public class Four {
    /**
     * 思路:先通过先序遍历找到根节点，因为在先序遍历中根节点一定比左右子树先出现
     * 然后根据获得的根节点将中序遍历的字符串分割成两个分进行递归
     * @param pre
     * @param in
     * @return
     */
    public static TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        TreeNode root=null;
        //1.如果前序遍历的长度为0，说明当前为空树
        if(pre.length==0){
            return null;
        }
        //2.构建根节点
        root=new TreeNode(pre[0]);

        //3.划分出左子树和右子树
        int len=in.length;
        int index=0;
        for (int i=0;i<len;i++){
            //找到中序遍历中根节点的位置
            if(in[i]==pre[0]){
                index=i;
                break;
            }
        }

        /**
         * 获得左右子树的先序遍历的数组
         */
        int[] leftPre=new int[index];
        int[] rightPre=new int[len-1-index];

        for (int j=1;j<len;j++){
            if(j<=index){
                leftPre[j-1]=pre[j];
            }else {
                rightPre[j-1-index]=pre[j];
            }
        }

        /**
         * 获得左右子树的中序遍历数组
         */
        int[] leftIn=new int[index];
        int[] rightIn=new int[len-1-index];

        for (int j=0;j<len;j++){
            if(j<index){
                leftIn[j]=pre[j];
            }
            if(j>index){
                rightIn[j-index-1]=pre[j];
            }
        }



//        if(leftIn.length==0&&rightPre.length==0){
//            return root;
//        }
//
//        if(leftIn.length==0){
//            root.right=reConstructBinaryTree(rightPre,rightIn);
//            return root;
//        }
//        if(rightPre.length==0){
//            root.left=reConstructBinaryTree(leftPre,leftIn);
//            return root;
//        }

        root.left=reConstructBinaryTree(leftPre,leftIn);
        root.right=reConstructBinaryTree(rightPre,rightIn);

        return root;
    }

    public static void main(String[] args) {

        int[] pre={1,2,3,4,5,6,7};
        int[] in={3,2,4,1,6,5,7};

        TreeNode treeNode = Four.reConstructBinaryTree(pre, in);

    }
}
