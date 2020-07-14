package demo.ConcurrentDemo.ForkJoin.SUM;

/**
 * 普通方法累加(单线程累加)
 * @author 曾鹏
 */
public class SunNormal {
    public static void main(String[] args) throws InterruptedException {
        int count=0;
        int[] src=MakeArray.makeArray();

        //开始时间
        long start=System.currentTimeMillis();

        for (int i = 0; i < src.length; i++) {
            Thread.sleep(1);
            count+=src[i];
        }
        System.out.println("The count is "+count+",it spend time: "+
                (System.currentTimeMillis()-start)+"ms");
    }
}
