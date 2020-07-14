package algorithm.offer;

/**
 * 类说明:合并两个递增的链表是合并后的链表也是递增的
 *
 * @author 曾鹏
 */
public class Fifteen {
    public ListNode Merge(ListNode list1,ListNode list2) {

        if(list1==null&&list2==null){
            return null;
        }
        if(list1==null){
            return list2;
        }
        if(list2==null){
            return list1;
        }

        ListNode temp=new ListNode(0);
        ListNode list3=temp;

        while(list1.next!=null||list2.next!=null){
            if(list1.val<list2.val){
                temp.next=list1;
                list1=list1.next;
            }else{
                temp.next=list2;
                list2=list2.next;
            }

            temp=temp.next;
        }

        return list3.next;

    }
}
