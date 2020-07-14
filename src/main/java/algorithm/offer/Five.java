package algorithm.offer;

import java.util.Stack;

/**
 * 类说明:使用两个栈来完成队列
 *
 * @author 曾鹏
 */
public class Five {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    //存入元素
    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        //找出队列的头
        while (!stack1.empty()){
            stack2.push(stack1.pop());
        }
        int temp=stack2.pop();
        //在将不需要弹出的数据返回stack1
        while (!stack2.empty()){
            stack1.push(stack2.pop());
        }
        return temp;
    }
}
