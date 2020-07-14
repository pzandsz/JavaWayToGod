package demo.ConcurrentDemo.Lock.Condition;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过Condition的await()和signalAll()模拟一个面试场景,
 * 场景如下:一共有三个面试官,面试官一次只能面试一个应聘者，当面试官在面试时，其他应聘者应当等待，
 * 当正在应聘的人面试结束后面试官会在应聘者队列中选择一个应聘者去面试，知道所有应聘者面试完。
 *
 * @author 曾鹏
 */
public class Interview {

    /**
     * 面试官队列
     */
    private LinkedList<InterviewerObj> interviewerLists;

    private Lock lock=new ReentrantLock();

    private Condition condition=lock.newCondition();

    public Interview(LinkedList<InterviewerObj> interviewerLists){
        this.interviewerLists=interviewerLists;
    }

    /**
     * 通知范式
     */
    public void callCandidate(InterviewerObj interviewerObj){
        if(interviewerObj!=null){
            //第一步，获得锁
            lock.lock();
            try {
                System.out.println("告诉应聘者们，面试官"+interviewerObj.getName()+"可以面试下一位应聘者了");
                interviewerLists.addLast(interviewerObj);
                condition.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                //释放锁
                lock.unlock();
            }

        }
    }

    /**
     * 等待范式
     */
    public InterviewerObj waitInterviewer(){
        //获得锁
        lock.lock();
        try {
            System.out.println("面试官们都在面试，请等待....");
            while (interviewerLists.isEmpty()) {
                condition.await();
            }
            return interviewerLists.removeFirst();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放锁
            lock.unlock();
        }

        return null;
    }

}
