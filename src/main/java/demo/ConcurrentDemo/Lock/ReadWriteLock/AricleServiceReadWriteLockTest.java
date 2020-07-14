package demo.ConcurrentDemo.Lock.ReadWriteLock;

/**
 * 测试基于ReadWriteLock的读写性能
 * 和synchronized实现的读写操作想对比，性能明显更好
 * 对比充分表明ReadWriteLock对处理读多写少的情况非常有效
 * @author 曾鹏
 */
public class AricleServiceReadWriteLockTest {

    /**
     * 读任务
     */
    static class ReadTask implements Runnable{

        ArticleServiceReadWriteLock articleService;

        public ReadTask(ArticleServiceReadWriteLock articleService){
            this.articleService=articleService;
        }

        @Override
        public void run() {
            long start=System.currentTimeMillis();
            articleService.read();
            System.out.println(Thread.currentThread().getName()+"读操作耗时:"+
                    (System.currentTimeMillis()-start));
        }
    }

    /**
     * 写任务
     */
    static class WriteTask implements Runnable{

        ArticleServiceReadWriteLock articleService;

        public WriteTask(ArticleServiceReadWriteLock articleService){
            this.articleService=articleService;
        }

        @Override
        public void run() {
            long start=System.currentTimeMillis();
            articleService.write();
            System.out.println(Thread.currentThread().getName()+"写操作耗时:"+
                    (System.currentTimeMillis()-start));
        }
    }

    public static void main(String[] args) {
        AricleServiceReadWriteLockTest test=new AricleServiceReadWriteLockTest();

        ArticleServiceReadWriteLock articleService=new ArticleServiceReadWriteLock();

        //模拟100个读操作
        for(int i=0;i<100;i++){
            new Thread(new AricleServiceReadWriteLockTest.ReadTask(articleService)).start();
        }

        //模拟3个写操作
        for (int i = 0; i < 3; i++) {
            new Thread((new AricleServiceReadWriteLockTest.WriteTask(articleService))).start();
        }

    }
}
