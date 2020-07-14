package algorithm.offer;

import java.util.ArrayList;

/**
 * 类说明:翻转链表
 *
 * @author 曾鹏
 */
public class Third {
    public static ArrayList<Integer> printListFromTailToHead(ListNode listNode) {

        ArrayList<Integer> list=new ArrayList<>();

        if(listNode==null){
            return list;
        }

        if(listNode.next==null){
            list.add(listNode.val);
            return list;
        }



        //用于处理第一个节点与第二个节点之间的循环指向问题
        boolean isToNull=true;
        ListNode pre=null;
        //将next指向当前节点的下一个节点,关键
        ListNode next=listNode.next;

        while (true){
            //1.将pre设置为当前节点
            pre=listNode;
            if(isToNull){
                isToNull=false;
                //将第一个节点的next指向null
                pre.next=null;
            }


            //3.将next指向的节点设为当前节点
            listNode=next;
            //4.将next指向当前节点的下一个节点
            next=next.next;
            //5.将当前节点的下一个节点指向pre
            listNode.next=pre;


            if(next==null){
                break;
            }

        }

        while (listNode.next!=null){
            list.add(listNode.val);

            listNode=listNode.next;
            if(listNode.next==null){
                list.add(listNode.val);
            }
        }




        return list;
    }

    public static void main(String[] args) {
        ListNode test=new ListNode(1);
//        ListNode temp=test;
//        for(int i=0;i<10;i++){
//            temp.next=new ListNode(i*10);
//            temp=temp.next;
//        }


        System.out.println(Third.printListFromTailToHead(test));
    }


}
