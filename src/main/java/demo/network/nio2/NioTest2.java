package demo.network.nio2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 类说明:测试channel的read
 *
 * @author 曾鹏
 */
public class NioTest2 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream=new FileInputStream("1.txt");

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer=ByteBuffer.allocate(100);
        //将通道中的数据读到buffer中 channel不提供直接读取也不允许直接读取
        channel.read(byteBuffer);

        System.out.println("flip之前: capacity="+byteBuffer.capacity());
        System.out.println("flip之前: limit="+byteBuffer.limit());
        System.out.println("flip之前: position="+byteBuffer.position());
        System.out.println("flip之前: mark="+byteBuffer.mark());

        /**
         * 源码中:
         *    public final Buffer mark() {
         *         mark = position;
         *         return this;
         *     }
         *
         *    初始化时mark=-1,
         *   初始化之后:有 0<=mark<=position<=limit<=capacity
         */
        byteBuffer.flip();


        System.out.println("flip之后: capacity="+byteBuffer.capacity());
        System.out.println("flip之后: limit="+byteBuffer.limit());
        System.out.println("flip之后: position="+byteBuffer.position());
        System.out.println("flip之后: mark="+byteBuffer.mark());

        while (byteBuffer.hasRemaining()){
            byte b = byteBuffer.get();
            System.out.println("Charactor:"+(char)b);
        }

        fileInputStream.close();

    }
}
