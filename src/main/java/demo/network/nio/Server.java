package demo.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * nio的网络模型:服务器端
 *
 */
public class Server {
    /**
     * 多路复用器
     */
    private Selector selector;


    public Server init(int port) throws IOException {
        /**
         * 获取一个ServerSocket通信
         */
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        /**
         * 设置为非阻塞的I/O通信模型
         */
        serverSocketChannel.configureBlocking(false);
        /**
         * 绑定制定的端口
         */
        serverSocketChannel.socket().bind(new InetSocketAddress(port));

        /**
         * 获得多路复用器
         */
        selector=Selector.open();
        /**
         * 将通道管理器与通道绑定，并为该通道注册SelectionKey.OP_ACCEPT(可连接就绪状态)事件
         * 只有当该事件到达时，Selector.select()会返回，否则一直阻塞。
         */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        return this;
    }

    public void listen() throws IOException {
        System.out.println("服务器端启动...");
        //使用轮询方式访问selector
        while (true){
            //获得可用的Channel数量
            selector.select();

            /**
             * 获得一个迭代器对象
             */
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                /**
                 * 获得一个SelectionKey对象
                 */
                SelectionKey key=iterator.next();
                iterator.remove();

                //客户端请求建立连接
                if(key.isAcceptable()){
                    ServerSocketChannel  server = (ServerSocketChannel) key.channel();
                    //获得服务端和客户端的连接通道
                    SocketChannel channel = server.accept();

                    // 向客户端发消息
                    channel.write(ByteBuffer.wrap(new String("send message to client:"+System.currentTimeMillis()).getBytes()));
                    // 在与客户端连接成功后，为客户端通道注册SelectionKey.OP_READ事件。
                }
                else if (key.isReadable()) {
                    // 获取客户端传输数据可读取消息通道。
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 创建读取数据缓冲器
                    ByteBuffer buffer = ByteBuffer.allocate(10);
                    int read = channel.read(buffer);
                    byte[] data = buffer.array();
                    String message = new String(data);

                    ByteBuffer outbuffer = ByteBuffer.wrap(message.getBytes());
                    channel.write(outbuffer);
                }

            }
        }



    }

    public static void main(String[] args) throws IOException {
        new Server().init(3231).listen();
    }


}
