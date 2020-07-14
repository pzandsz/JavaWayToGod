package demo.network.nio.detail;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

public class ClientSocketTest {

    private int size = 1024;
    private ByteBuffer byteBuffer;
    private SocketChannel socketChannel;

    public void connectServer() throws IOException {
        //创建一个客户端通道
        socketChannel = SocketChannel.open();
        //连接
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8998));
        byteBuffer = ByteBuffer.allocate(size);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        receive();
    }

    /**
     * 接受来之服务器端的消息
     * @throws IOException
     */
    private void receive() throws IOException {
        while (true) {
            int count;
            byteBuffer.clear();
            while ((count = socketChannel.read(byteBuffer)) > 0) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    System.out.print((char) byteBuffer.get());
                }

                byteBuffer.clear();
            }
        }
    }

    private void send(byte[] data) throws IOException {
        byteBuffer.clear();
        byteBuffer.put(data);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }

    public static void main(String[] args) throws IOException {
        new ClientSocketTest().connectServer();
    }
}

