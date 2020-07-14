package algorithm.offer;
import java.util.ArrayList;


public class Ninteen {
    public ArrayList<Integer> printMatrix(int [][] matrix) {

        if(matrix==null){
            return null;
        }
        ArrayList<Integer> printArray=new ArrayList<Integer>();

        //定义行
        int row=0;
        //定义列
        int column=0;

        int rowLen=matrix.length;
        int columnLen=matrix[0].length;


        if(rowLen==1&&columnLen==1){
            printArray.add(matrix[0][0]);
            return printArray;
        }



        //从左向右一行
        for(int j=0;j<columnLen;j++){
            printArray.add(matrix[row][column]);
            column++;
        }
        column--;

        //自上而下一列
        for(int i=1;i<rowLen;i++){
            row++;
            printArray.add(matrix[row][column]);
        }


        //从右向左一行
        for(int j=0;j<columnLen-1;j++){
            column--;
            printArray.add(matrix[row][column]);

        }
//        System.out.println("row:"+row+"column:"+column);
        //自下而上一列
        for(int i=0;i<rowLen-2;i++){
            row--;
            printArray.add(matrix[row][column]);
        }

        rowLen-=2;
        columnLen-=2;
        ArrayList<Integer> temp = printMatrix2(matrix, row, ++column, rowLen,columnLen);
        if (temp!=null){
            printArray.addAll(temp);
        }

        return printArray;
    }


    public ArrayList<Integer> printMatrix2(int [][] matrix,int row,int column,int rowLen,int columnLen) {
        if(rowLen<=0||columnLen<=0){
            return null;
        }


        ArrayList<Integer> printArray=new ArrayList<Integer>();

        if(rowLen==1&&columnLen==1){
            printArray.add(matrix[row][column]);
            return printArray;
        }




        //从左向右一行
        for(int j=0;j<columnLen;j++){
            printArray.add(matrix[row][column]);
            column++;
        }
        column--;

        //自上而下一列
        for(int i=1;i<rowLen;i++){
            row++;
            printArray.add(matrix[row][column]);
        }


        //从右向左一行
        for(int j=0;j<columnLen-1;j++){
            column--;
            printArray.add(matrix[row][column]);

        }
        System.out.println("row:"+rowLen+"column:"+columnLen);
        //自下而上一列
        for(int i=0;i<rowLen-2;i++){
            row--;
            printArray.add(matrix[row][column]);
        }

        rowLen-=2;
        columnLen-=2;
        ArrayList<Integer> temp = printMatrix2(matrix, row, ++column, rowLen,columnLen);
        if (temp!=null){
            printArray.addAll(temp);
        }

        return printArray;
    }

    public static void main(String[] args) {
        int[][] array=new int[4][5];
        for (int i=0;i<4;i++){
            for (int j=0;j<5;j++){
                array[i][j]=i*5+j+1;
            }
        }

//        for (int i=0;i<4;i++){
//            for (int j=0;j<4;j++){
//                System.out.print(array[i][j]+" ");
//            }
//            System.out.println();
//        }


        Ninteen ninteen=new Ninteen();
        ArrayList<Integer> list = ninteen.printMatrix(array);
        for (int i=0;i<list.size();i++){
            System.out.print(list.get(i)+" ");
        }
    }
}