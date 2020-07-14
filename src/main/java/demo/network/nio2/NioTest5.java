package demo.network.nio2;

import java.nio.ByteBuffer;

/**
 * 类说明:测试Buffer提供的类型化put和get
 *
 * 用来处理协议
 *
 * @author 曾鹏
 */
public class NioTest5 {
    public static void main(String[] args) {

        ByteBuffer buffer=ByteBuffer.allocate(100);
        buffer.put((byte) 1);
        buffer.putInt(22);
        buffer.putChar('A');
        buffer.putDouble(25.12);
        buffer.putShort((short) 11);
        buffer.putChar('B');

        buffer.flip();
        /**
         * 当以写入的顺序读取数据时，读取的数据不会出现问题，
         * 但是如果顺序一旦改变，将会造成读取的数据一片混乱，
         * 因为每种类型的长度是不一样的，而他们在buffer中都是以字节为单位去获取，
         * 加那这个例子来来讲: byte 1 , int 4, char 4, double 8, short 2 ,char 4
         * 如果先获得一个int,那实际上是获取了 byte和int的结合
         */
        System.out.println(buffer.get());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());

    }
}
