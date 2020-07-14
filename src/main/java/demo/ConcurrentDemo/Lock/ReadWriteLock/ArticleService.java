package demo.ConcurrentDemo.Lock.ReadWriteLock;

/**
 * 模拟读写文章的业务
 * @author 曾鹏
 */
public class ArticleService {

    private Object article;

    public ArticleService(Object article) {
        this.article = article;
    }

    /**
     * 读文章
     */
    public void readArticle(){

        synchronized (article){
//            System.out.println("读文章....");
            //模拟读过程
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("读完文章。");
        }
    }

    /**
     * 写操作
     */
    public void writeArticle(){
        synchronized (article){
//            System.out.println("修改文章....");
            //模拟修改过程
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("修改成功。");
        }
    }
}
