package algorithm.offer;

import java.util.Stack;

/**
 * 类说明:
 *输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。
 *  假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，
 *  序列4,5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。
 * （注意：这两个序列的长度是相等的）
 * @author 曾鹏
 */
public class Twenty {

    /**
     *使用一个栈来模拟
     */
    private Stack stack=new Stack();

    public boolean IsPopOrder(int [] pushA,int [] popA) {

        if(pushA.length!=popA.length){
            return false;
        }

        int j=0;
        for(int i=0;i<pushA.length;i++){
            stack.push(pushA[i]);
            if(pushA[i]==popA[j]){
                stack.pop();
                j++;
            }
        }

        for(int k=0;k<stack.size();k++){
            if(j>popA.length||(Integer) stack.pop()!=popA[j]){
                return false;
            }
            j++;
        }

        return true;

    }

    public static void main(String[] args) {
        Twenty twenty=new Twenty();
        int[] pushA={1,2,3,4,5};
        int[] popA={4,3,5,2,1};

        System.out.println(twenty.IsPopOrder(pushA,popA));
    }

}
