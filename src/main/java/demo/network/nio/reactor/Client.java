package demo.network.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class Client {


    SocketChannel socketChannel;

    public Client() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",1234));
    }

    public void readMsg() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.read(buffer);
        buffer.flip();
        byte[] bytes = buffer.array();
        System.out.println(new String(bytes));
        buffer.clear();
    }


    public void sendMsg(String msg) throws IOException {
        socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        for(int i=0;i<3;i++){
            Client client=new Client();
            client.sendMsg("3211465789");
            System.out.println("-------------");
            client.readMsg();
        }
        Thread.sleep(5000);
    }
}
