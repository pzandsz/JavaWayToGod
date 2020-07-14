package demo.ConcurrentDemo.ForkJoin.MergeSort;

/**
 * 单线程实现归并排序
 * @author 曾鹏
 */
public class MergeSort {

    /**
     * 归并排序,对整形数据的归并排序
     */
    public static void merge(int[] list){
        if(list.length>1){
            int mid=list.length/2;
            int[] left=new int[mid];
            System.arraycopy(list,0,left,0,mid);
            merge(left);

            int[] right=new int[list.length-mid];
            System.arraycopy(list,mid,right,0,list.length-mid);
            merge(right);

            merge(left,right,list);
        }
    }

    public static void merge(int[] left,int[] right,int[] temp){
        int current1=0;
        int current2=0;
        int current3=0;

        while (current1<left.length&&current2<right.length){
            if(left[current1]<right[current2]){
                temp[current3++]=left[current1++];
            }else {
                temp[current3++]=right[current2++];
            }
        }

        while (current1<left.length){
            temp[current3++]=left[current1++];
        }

        while (current2<right.length){
            temp[current3++]=right[current2++];
        }
    }

    public static void main(String[] args) {
        int[] list={2,3,2,5,6,1,-2,3,14,12};
        merge(list);
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i]+" ");
        }
    }
}
