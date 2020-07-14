package algorithm.offer;

/**
 * 类说明:输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
 * 使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，
 * 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 *
 * @author 曾鹏
 */
public class Thirteen {
    public void reOrderArray(int [] array) {
        int len=array.length;
        int[] swapArray=new int[len];
        int head=0;
        int tail=len-1;
        int lastIndex=tail;

        for(int i=0;i<len;i++){
            if(array[i]%2!=0){
                swapArray[head]=array[i];
                head++;
            }

            if(array[lastIndex-i]%2==0){
                swapArray[tail]=array[lastIndex-i];
                tail--;
            }

            if(tail<=head){
                break;
            }
        }

        array=swapArray;

        for (Integer i:array) {
            System.out.print(i+" ");
        }
    }

    public static void main(String[] args) {
        Thirteen thirteen=new Thirteen();

        int[] array={1,2,3,4,5,6,7};

        thirteen.reOrderArray(array);
    }
}
