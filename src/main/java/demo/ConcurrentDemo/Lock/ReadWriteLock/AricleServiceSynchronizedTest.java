package demo.ConcurrentDemo.Lock.ReadWriteLock;

import javax.management.relation.RoleUnresolved;
import java.util.Random;

/**
 * 测试使用synchronized的读写性能
 * 根据测试结果，我们能够发现，读写花费的时间随着线程数量的增加则增加
 * @author 曾鹏
 */
public class AricleServiceSynchronizedTest {


    /**
     * 读任务
     */
    static class ReadTask implements Runnable{

        ArticleService articleService;

        public ReadTask(ArticleService articleService){
            this.articleService=articleService;
        }

        @Override
        public void run() {
            long start=System.currentTimeMillis();
            articleService.readArticle();
            System.out.println(Thread.currentThread().getName()+"读操作耗时:"+
                    (System.currentTimeMillis()-start));
        }
    }

    /**
     * 写任务
     */
    static class WriteTask implements Runnable{

        ArticleService articleService;

        public WriteTask(ArticleService articleService){
            this.articleService=articleService;
        }

        @Override
        public void run() {
            long start=System.currentTimeMillis();
            articleService.writeArticle();
            System.out.println(Thread.currentThread().getName()+"写操作耗时:"+
                    (System.currentTimeMillis()-start));
        }
    }

    public static void main(String[] args) {
        AricleServiceSynchronizedTest test=new AricleServiceSynchronizedTest();

        ArticleService articleService=new ArticleService(new Object());

        //模拟100个读操作
        for(int i=0;i<100;i++){
            new Thread(new ReadTask(articleService)).start();
        }

        //模拟3个写操作
        for (int i = 0; i < 3; i++) {
            new Thread((new WriteTask(articleService))).start();
        }

    }

}
