package demo.network.nio2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 类说明:测试channel的写
 *
 * @author 曾鹏
 */
public class NioTest3 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream=new FileOutputStream("1.txt");

        FileChannel channel = fileOutputStream.getChannel();

        try {
            channel.write(ByteBuffer.wrap("Hello NIO".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileOutputStream.close();

    }
}
