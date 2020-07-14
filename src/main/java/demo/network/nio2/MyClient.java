package demo.network.nio2;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class MyClient {
    /**
     * 有问题: socketChannel.connect(new InetSocketAddress("localhost",3231));放在open()之前会出个bug
     * @throws IOException
     */
    public static void start() throws IOException {
        SocketChannel socketChannel=SocketChannel.open();

        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        //connect,发起连接
        socketChannel.connect(new InetSocketAddress("localhost",3231));

        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        //轮询是否有来至服务器的消息
        while (true){
            int select = selector.select();
            System.out.println(select);


            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("size:"+selectionKeys.size());
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                System.out.println("是否连接:"+key.isConnectable());
                if(key.isConnectable()){

                    //处理连接事件
                    SocketChannel channel= (SocketChannel) key.channel();

                    if(channel.isConnectionPending()){
                        channel.finishConnect();
                    }

                    ByteBuffer buffer=ByteBuffer.allocate(1024);
                    channel.read(buffer);
                    System.out.println("客户端收到:"+buffer.toString());


                    channel.register(selector,SelectionKey.OP_READ);
                }else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1023);
                    channel.read(buffer);
                    byte[] data = buffer.array();
                    String message = new String(data);

                    System.out.println("recevie message from server:, size:"
                            + buffer.position() + " msg: " + message);
                    ByteBuffer outbuffer = ByteBuffer.wrap(("client.".concat(message)).getBytes());
                    channel.write(outbuffer);
                }
            }

        }

    }


    public static void main(String[] args) throws IOException {
//        MyClient.start();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",3231));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.read(buffer);
        buffer.flip();
        byte[] bytes = buffer.array();
        System.out.println(new String(bytes));
    }
}
