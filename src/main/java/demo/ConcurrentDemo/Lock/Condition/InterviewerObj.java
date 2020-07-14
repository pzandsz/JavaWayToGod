package demo.ConcurrentDemo.Lock.Condition;

import java.util.concurrent.Semaphore;

/**
 * 面试官对象
 * @author 曾鹏
 */
public class InterviewerObj {

    /**
     * 面试官姓名
     */
    private String name;

    /**
     * 面试时间
     */
    private int time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public InterviewerObj(String name, int time){
        this.name=name;
        this.time=time;
    }
}
