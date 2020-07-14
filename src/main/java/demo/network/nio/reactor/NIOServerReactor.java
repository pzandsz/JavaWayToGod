package demo.network.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 精典Reactor模式
 * @author 曾鹏
 *
 *
 * 经典Reactor模式中，尽管一个线程可同时监控多个请求（Channel），
 * 但是所有读/写请求以及对新连接请求的处理都在同一个线程中处理，
 * 无法充分利用多CPU的优势，同时读/写操作也会阻塞对新连接请求的处理
 */
public class NIOServerReactor {


    public static void main(String[] args) throws IOException {

        //1.获得Selector对象
        Selector selector = Selector.open();
        //2.获得ServerSocketChannel对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //3.设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //4.将selector和channel绑定到一起，让selector能够监听channel
        serverSocketChannel.bind(new InetSocketAddress(1234));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        /**
         * selector.select()是阻塞的，
         * 当有至少一个通道可用时该方法返回可用通道个数。
         * 同时该方法只捕获Channel注册时指定的所关注的事件。
         */
        while (true) {
            selector.select();
            //获得已选择键的集合
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            /**
             * 一个keys集合中可能有多个channel对应的key
             * 每个key都是SelectionKey,保存着channel与selector的关系
             */
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                //是否可以连接?
                if (key.isAcceptable()) {
                    //通过SelectionKey获得服务器端通道channel
                    ServerSocketChannel acceptServerSocketChannel =
                            (ServerSocketChannel) key.channel();
                    //获得与服务器端建立连接的客户端(从服务器端获得客户端)
                    SocketChannel socketChannel = acceptServerSocketChannel.accept();
                    System.out.println("服务器端接收到来至"+ socketChannel.getRemoteAddress()+"的消息.");
                    //将客户端通道设置为非阻塞
                    socketChannel.configureBlocking(false);

                    //将客户端的读事件注册到SelectorKey中，selector可以监听到
                    socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);


                    //向客户端写数据
                    socketChannel.write(ByteBuffer.wrap("123，客户端已注册读写事件".getBytes()));
                    System.out.println("------------------");

                } else if (key.isReadable()) {
                    System.out.println("处理读事件...");
                    //是否可读?
                    SocketChannel socketChannel = (SocketChannel) key.channel();

                    //创建缓冲区对象
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int count = socketChannel.read(buffer);
                    if (count <= 0) {
                        socketChannel.close();
                        key.cancel();
                        System.out.println("未收到数据，客户端关闭连接");
                        continue;
                    }
                    System.out.println("收到信息:"+new String(buffer.array()));
                }
                //将已选择键集合中的对应的key去除
                keys.remove(key);

            }
        }
    }
}
