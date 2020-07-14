package algorithm.offer;

import java.util.HashMap;

/**
 * 类说明:输入两个链表，找出它们的第一个公共结点。
 *
 * @author 曾鹏
 */
public class FindFirstCommonNodeClass {
    /**
     * 利用HashMap的特性
     * 将一个链表的每个节点存入map中，接着对另一个链表进行遍历,
     *
     * @param pHead1
     * @param pHead2
     * @return
     */
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {





        ListNode current1 = pHead1;
        ListNode current2 = pHead2;


        HashMap<ListNode, Integer> hashMap = new HashMap<ListNode, Integer>();
        while (current1 != null) {
            hashMap.put(current1, null);
            current1 = current1.next;
        }
        while (current2 != null) {
            if (hashMap.containsKey(current2)){

                return current2;
            }
            current2 = current2.next;
        }


        return null;
    }


}
