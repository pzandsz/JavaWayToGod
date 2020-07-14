package algorithm;

import algorithm.offer.ListNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class MianTest2 {





    /**
     * 判断是否为回文
     * @param head
     * @return
     */
    public static boolean isHuiwen(ListNode head) {
        List<Integer> vals = new ArrayList<>();

        //将给定链表复制一份
        ListNode currentNode = head;
        while (currentNode != null) {
            vals.add(currentNode.val);
            currentNode = currentNode.next;
        }

        //设置头尾指针
        int front = 0;
        int back = vals.size() - 1;
        while (front < back) {

            if (!vals.get(front).equals(vals.get(back))) {
                return false;
            }
            front++;
            back--;
        }
        return true;
    }


    /**
     * 反转链表
     * @param head
     * @return
     */
    public static ListNode changeNode(ListNode head,int k) {
        //将头结点置为尾结点
        ListNode temp1 = head;
        temp1.next=null;

        while (head!=null){


            ListNode temp2 = head.next;

            temp2.next=temp1;
        }
        return null;

    }

}


