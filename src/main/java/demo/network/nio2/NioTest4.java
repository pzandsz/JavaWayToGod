package demo.network.nio2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 类说明:NIO实现文件输入输出
 *
 * @author 曾鹏
 */
public class NioTest4 {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream=new FileInputStream("input.txt");
        FileOutputStream outputStream=new FileOutputStream("output.txt");


        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        /**
         * 将input.txt的文件输入到output.txt中
         */

        ByteBuffer byteBuffer =ByteBuffer.allocate(100);
        inputChannel.read(byteBuffer);

        byteBuffer.flip();

        System.out.println(byteBuffer);

        //clear后恢复到初始化状态
        byteBuffer.clear();

        outputChannel.write(byteBuffer);


        inputStream.close();
        outputStream.close();



    }
}
