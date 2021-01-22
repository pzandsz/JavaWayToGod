package demo.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * nio的网络模型:服务器端
 * 1.应用进程 系统调用recvfrom
 * 2.内核是否准备好数据
 *      否：立即返回数据未准备好，进程不阻塞，重复操作1，2
 *      是：立即返回数据已准备好，进程不阻塞
 * 3.复制数据报，将数据从内核空间复制到用户空间
 * 4.复制完成，返回数据包
 *
 * 每次应用进程询问内核是否有数据报准备好，当有数据报准备好时，就进行拷贝数据报的操作，
 * 从内核拷贝到用户空间，和拷贝完成返回的这段时间，应用进程是阻塞的。但在没有数据报准备好时，
 * 并不会阻塞程序，内核直接返回未准备就绪的信号，等待应用进程的下一个轮询。
 * 但是，轮询对于 CPU 来说是较大的浪费，一般只有在特定的场景下才使用。
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
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
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
        selector = Selector.open();
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
                SelectionKey key = iterator.next();
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

                    ByteBuffer outBuffer = ByteBuffer.wrap(message.getBytes());
                    channel.write(outBuffer);
                }

            }
        }



    }

    public static void main(String[] args) throws IOException {
        new Server().init(3231).listen();
    }


}
