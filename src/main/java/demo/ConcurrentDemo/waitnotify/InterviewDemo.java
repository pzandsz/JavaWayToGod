package demo.ConcurrentDemo.waitnotify;

import java.util.ArrayList;

/**
 * 类说明:如果进行一场面试，只有一个面试官，面试官一个时间段内只会面试一个求职者，
 * 如果面试官正在面试的话，其他求职者必须等待，当面试官面试完一个求职者后，
 * 会通知下一个求职者进行面试，使用等待通知模型模拟这个场景。
 *
 * @author 曾鹏
 */
public class InterviewDemo {



    public void test() throws InterruptedException {
        Interviewer interviewer=new Interviewer();

        //创建三个面试者
        JobSeeker jobSeekerA=new JobSeeker("张三");
        JobSeeker jobSeekerB=new JobSeeker("李四");
        JobSeeker jobSeekerC=new JobSeeker("王五");

        interviewer.jobSeelerWait(jobSeekerA);
        interviewer.jobSeelerWait(jobSeekerB);
        interviewer.jobSeelerWait(jobSeekerC);


    }

    public static void main(String[] args) throws InterruptedException {
        InterviewDemo demo=new InterviewDemo();
        demo.test();
    }


     class Interviewer{

        private ArrayList<JobSeeker> jobSeekers=new ArrayList<>();

        private Object lock=new Object();

        /**
         * 当前面试官是否在面试中，默认为false
         */
        private boolean isInterview=false;

        /**
         * 求职者等待
         */
        public void jobSeelerWait(JobSeeker jobSeeker) throws InterruptedException {
            if(!isInterview){
                isInterview=true;
                interview(jobSeeker);
            }else {
                //等待,进入等待列表
                jobSeekers.add(jobSeeker);
                //进入阻塞状态
                synchronized (lock){
                    jobSeeker.wait();
                }

                interview(jobSeeker);
            }

        }

        /**
         * 从等待队列中获唤醒第一个求职者
         */
        public void jobSeelerNotify(){
            if(jobSeekers.isEmpty()){
                System.out.println("面试结束....");
                isInterview=false;
            }else {
                JobSeeker jobSeeker = jobSeekers.get(0);
                //从等待队列中移除
                jobSeekers.remove(jobSeeker);

                synchronized (lock){
                    jobSeeker.notify(); //需要注意jobSeeker一定要确保与执行wait方法的是同一个
                }
            }
        }

        /**
         * 面试过程
         * @param jobSeeker
         * @throws InterruptedException
         */
        public void interview(JobSeeker jobSeeker) throws InterruptedException {
            System.out.println(jobSeeker.name+"开始面试...");
            //模拟面试过程 1秒钟
            Thread.sleep(1000);

            isInterview=false;
            //唤醒一个求职者
            jobSeelerNotify();
        }
    }

    class JobSeeker{

        private String name;

        public JobSeeker(String name){
            this.name=name;
        }
    }
}
