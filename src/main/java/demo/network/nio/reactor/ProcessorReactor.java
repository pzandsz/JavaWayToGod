package demo.network.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在Processor中，同样创建了一个静态的线程池，且线程池的大小为机器核数的两倍。
 * 每个Processor实例均包含一个Selector实例。同时每次获取Processor实例时均提交一个任务到该线程池，
 * 并且该任务正常情况下一直循环处理，不会停止。而提交给该Processor的SocketChannel通过在其Selector注册事件，
 * 加入到相应的任务中。由此实现了每个子Reactor包含一个Selector对象，并由一个独立的线程处理。
 *
 * @author 曾鹏
 */
public class ProcessorReactor {
    /**
     * 创建一个线程池
     */
    private static final ExecutorService service =
            Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());


    private Selector selector;

    public ProcessorReactor() throws IOException {
        //创建一个Selector
        this.selector = SelectorProvider.provider().openSelector();
        start();
    }

    /**
     * 添加通道
     * @param socketChannel
     * @throws ClosedChannelException
     */
    public void addChannel(SocketChannel socketChannel) throws ClosedChannelException {
        socketChannel.register(this.selector, SelectionKey.OP_READ);
    }


    /**
     * 某个线程调用select()方法后阻塞了，即使没有通道已经就绪，也有办法让其从select()方法返回。
     * 只要让其它线程在第一个线程调用select()方法的那个对象上调用Selector.wakeup()方法即可。
     * 阻塞在select()方法上的线程会立马返回。
     */
    public void wakeup() {
        this.selector.wakeup();
    }

    public void start() {
        //lambda表达式
        service.submit(() -> {
            while (true) {
                if (selector.select(500) <= 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        int count = socketChannel.read(buffer);
                        if (count < 0) {
                            socketChannel.close();
                            key.cancel();
                            System.out.println("{}\t Read ended"+ socketChannel);
                            continue;
                        } else if (count == 0) {
                            System.out.println("{}\t Message size is 0"+ socketChannel);
                            continue;
                        } else {
                            System.out.println("{}\t Read message {}"+
                                    socketChannel+","+new String(buffer.array()));
                        }
                    }
                }
            }
        });
    }
}
