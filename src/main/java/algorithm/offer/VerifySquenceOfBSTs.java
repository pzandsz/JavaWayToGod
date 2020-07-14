package algorithm.offer;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class VerifySquenceOfBSTs {
    public boolean VerifySquenceOfBST(int [] sequence) {
        int len=sequence.length;
        int root=sequence[len-1];

        if(sequence==null){
            return false;
        }

        if(len<=1){
            return true;
        }

        int index=-1;
        for(int i=0;i<len-1;i++){
            if(index<0&&sequence[i]>root){
                index=i;
            }else{
                if(index>=0&&sequence[i]<root){
                    return false;
                }
            }
        }



        //验证左子树和右子树
        return VerifySquenceOfBST2(sequence,0,index-1)&&VerifySquenceOfBST2(sequence,index,len-1);

    }

    public boolean VerifySquenceOfBST2(int [] sequence,int head,int tail) {
        int len=tail-head;


        if(len<=1){
            return true;
        }
        int root=sequence[head+len-1];

        int index=-1;

        for(int i=0;i<len-1;i++){
            if(index<0&&sequence[i+head]>root){
                index=+head;
            }else{
                if(index>=0&&sequence[i+head]<root){
                    return false;
                }
            }
        }

        if(index==-1){
            //验证左子树
            return VerifySquenceOfBST2(sequence,0,len-2);
        }


        //验证左子树和右子树
        return VerifySquenceOfBST2(sequence,0,index-1)&&VerifySquenceOfBST2(sequence,index,len-1);

    }

    public static void main(String[] args) {

        int[] test={4,8,6,12,16,14,10};
        VerifySquenceOfBSTs bst=new VerifySquenceOfBSTs();
        System.out.println(bst.VerifySquenceOfBST(test));
    }
}
