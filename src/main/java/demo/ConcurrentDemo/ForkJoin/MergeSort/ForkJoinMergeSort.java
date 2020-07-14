package demo.ConcurrentDemo.ForkJoin.MergeSort;

import demo.ConcurrentDemo.ForkJoin.SUM.ForkJoinSum;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用Fork/Join实现归并排序
 */
public class ForkJoinMergeSort {

    static class MergeTask extends RecursiveTask<Integer[]>{

        private Integer[] list;

        public MergeTask(Integer[] list){
            this.list=list;
        }

        /**
         * 归并算法
         */
        public void merge(Integer[] leftArray,Integer[] rightArray,Integer[] temp){
            int current1=0;
            int current2=0;
            int current3=0;

            //合并两个分割的数组
            while (current1<leftArray.length&&current2<rightArray.length){
                if(leftArray[current1]<rightArray[current2]){
                    temp[current3++]=leftArray[current1++];
                }else {
                    temp[current3++]=rightArray[current2++];
                }
            }

            //如果有为添加到temo中的补充进去
            while (current1<leftArray.length){
                temp[current3++]=leftArray[current1++];
            }

            while (current2<rightArray.length){
                temp[current3++]=rightArray[current2++];
            }
        }


        @Override
        protected Integer[] compute() {

            if(list.length>1){
                int mid=list.length/2;
                Integer[] left=new Integer[mid];
                System.arraycopy(list,0,left,0,mid);

                int len=list.length;
                Integer[] right=new Integer[len-mid];
                System.arraycopy(list,mid,right,0,len-mid);

                //划分子任务
                MergeTask leftTask=new MergeTask(left);
                MergeTask rightTask=new MergeTask(right);

                //提交
                invokeAll(leftTask,rightTask);

                //合并左和右
                merge(leftTask.join(),rightTask.join(),list);

            }
            return list;
        }

    }

    public static void main(String[] args) {
        Integer[] list={2,3,2,5,6,1,-2,3,14,12};
        //new出池的实例
        ForkJoinPool pool=new ForkJoinPool();

        //new出一个
        ForkJoinMergeSort.MergeTask innerMerge=new  ForkJoinMergeSort.MergeTask(list);

        //开始时间
//        long start=System.currentTimeMillis();

        pool.invoke(innerMerge);

        list = innerMerge.join();
        for (Integer i:list) {
            System.out.print(i+" ");
        }
    }
}
