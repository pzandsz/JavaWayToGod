package algorithm.offer;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class First {
    public boolean Find(int target, int [][] array) {

        int len=array.length;


        if(len==0||len!=array[0].length){
            return false;
        }



        for(int i=0;i<len;i++){
            if(target==array[i][len-1]){
                return true;
            }

            if(target<array[i][len-1]){
                //检查该行
                if(checkRow(target,0,len-1,array[i])){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkRow(int target,int head,int tail,int[] row){
        if(head>=tail&&row[head]==target){
            return true;
        }
        if(head>=tail&&row[head]!=target){
            return false;
        }

        int mid=(head+tail)/2;
        if(row[mid]==target){
            return true;
        }else if(row[mid]>target){
            return checkRow(target,head,mid-1,row);
        }else{
            return checkRow(target,mid+1,tail,row);
        }
    }


    public static void main(String[] args) {
//        int[][] test={
//                {1,2,8,9},
//                {2,4,9,12},
//                {4,7,10,13},
//                {6,8,11,15}};

        int[][] test={};
        First first=new First();

        System.out.println(first.Find(1,test));
    }
}
