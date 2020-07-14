package demo.ConcurrentDemo.Lock.Condition;

import java.util.LinkedList;

/**
 * 测试:面试场景的进行
 * @author 曾鹏
 */
public class InterviewTest {

    static class CandidateTask implements Runnable{

        Interview interview;
        public CandidateTask(Interview interview){
            this.interview=interview;
        }

        @Override
        public void run() {
            InterviewerObj interviewerObj =interview.waitInterviewer();
            if (interviewerObj!=null){
                System.out.println(Thread.currentThread().getName()+
                        "应聘者被"+interviewerObj.getName()+"面试"+interviewerObj.getTime()+"分钟...");
                //模拟面试时间
                try {
                    Thread.sleep(interviewerObj.getTime()*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //面试结束
                System.out.println("面试结束....");
                interview.callCandidate(interviewerObj);
            }
        }
    }

    public static void main(String[] args) {
        //初始化面试者容量
        LinkedList<InterviewerObj> linkedList=new LinkedList<>();
        for (int i=1;i<=3;i++){
            linkedList.add(new InterviewerObj(i+"",i));
        }

        Interview interview=new Interview(linkedList);

        //模拟100个应聘者
        for (int i=0;i<100;i++){
            new Thread(new CandidateTask(interview)).start();
        }

    }
}
