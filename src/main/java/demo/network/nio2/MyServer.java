package demo.network.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 类说明:服务器端，能够接受客户端的信息并每隔1分钟向客户端发送当前时间
 *
 * @author 曾鹏
 */
public class MyServer {
    public static void start() throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector=Selector.open();


        serverSocketChannel.bind(new InetSocketAddress(3231));

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            int select = selector.select();
            if(select==0){
                System.out.println("出现空轮询bug");
                break;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selected = iterator.next();
                iterator.remove();

                if(selected.isAcceptable()){
                    System.out.println("1.连接成功");
                    //处理连接请求
                    ServerSocketChannel serverChannel= (ServerSocketChannel) selected.channel();

                    SocketChannel channel = serverChannel.accept();
                    System.out.println(1);
                    byte[] buffer="建立连接成功..".getBytes();
                    channel.write(ByteBuffer.wrap(buffer));


                }else if(selected.isReadable()){
                    //处理读事件
                    SocketChannel channel = (SocketChannel) selected.channel();


                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer);
                    byte[] data = buffer.array();
                    String message = new String(data);

                    ByteBuffer outbuffer = ByteBuffer.wrap(message.getBytes());
                    channel.write(outbuffer);
                }
            }


        }

    }

    public static void main(String[] args) throws IOException {
        start();
    }
}
