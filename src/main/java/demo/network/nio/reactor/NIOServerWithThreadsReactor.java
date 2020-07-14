package demo.network.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Iterator;
import java.util.Set;

/**
 * 基于多线程的Reactor模式
 * @author 曾鹏
 *
 * 引入多线程，并行处理多个读/写操作,提高系统的处理速度
 *
 */
public class NIOServerWithThreadsReactor {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(1234));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //死循环,一直轮询
        while (true) {
            //selectNow(),非阻塞的获取
            if(selector.selectNow() < 0) {
                continue;
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = acceptServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("服务器端收到来至"+socketChannel.getRemoteAddress()+"的消息.");
                    SelectionKey readKey = socketChannel.register(selector, 0);
                    /**
                     * attach对象及取出该对象是NIO提供的一种操作，
                     * 但该操作并非Reactor模式的必要操作，本文使用它，只是为了方便演示NIO的接口。
                     *
                     * 附加Processor对象,用于获取读信息
                     */
                    readKey.attach(new Processor());
                    readKey.interestOps(SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    //可读事件被监测到,因为在给客户端channel注册读事件监听是，附加了一个Process对象
                    //所以，在处理读事件是可以直接获得Process对象。
                    //基于这种机制，可以实现在注册读事件时就定义好处理读事件的处理流程

                    //获得附加对象，并通过附加对象进行读操作
                    System.out.println("isReadable");
                    Processor processor = (Processor) key.attachment();
                    processor.process(key);

                    //处理结束后，取消连接
                    key.cancel();

                }else if(key.isWritable()){
                    System.out.println("isWritable");
                }
            }
        }
    }
}
