package demo.ConcurrentDemo.ForkJoin.SUM;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用Fork/Join模型（多线程模型）来计算数组累加和
 */
public class ForkJoinSum {

    /**
     * 继承RecursiveTask,代表是一个具有返回值的任务
     */
    private static class SumTask extends RecursiveTask<Integer>{

        //阀值
        private final  static int THRESHOLD=MakeArray.ARRAY_LENGTH/10;
        private int[] src;
        private int fromIndex;
        private int toIndex;

        public SumTask(int[] src,int fromIndex,int toIndex){
            this.src=src;
            this.fromIndex=fromIndex;
            this.toIndex=toIndex;
        }

        @Override
        protected Integer compute(){
            //判断任务大小是否合适
            if(toIndex-fromIndex<THRESHOLD){
//                System
                int count=0;
                for(int i=fromIndex;i<=toIndex;i++){
                    try {
                        Thread.sleep(1);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    count+=src[i];
                }
                return count;
            }else {
                int mid=(fromIndex+toIndex)/2;
                SumTask left=new SumTask(src,fromIndex,mid);
                SumTask right=new SumTask(src,mid+1,toIndex);
                //提交给pool对象
                invokeAll(left,right);
//                System.out.println(left.join()+right.join());
                return left.join()+right.join();
            }
        }
    }

    public static void main(String[] args) {
        int[] src=MakeArray.makeArray();

        //new出池的实例
        ForkJoinPool pool=new ForkJoinPool();

        //new出一个
        SumTask innerFind=new SumTask(src,0,src.length-1);

        //开始时间
        long start=System.currentTimeMillis();

        pool.invoke(innerFind);

        System.out.println("The count is "+innerFind.join()+",it spend "+
                (System.currentTimeMillis()-start)+"ms");
    }
}

