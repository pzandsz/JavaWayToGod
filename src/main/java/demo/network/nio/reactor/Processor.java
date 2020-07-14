package demo.network.nio.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 该类中是具体处理读请求的,同时将对新连接的处理和读/写操作的处理放在了不同的线程中，
 * 读/写操作不再阻塞对新连接请求的处理。
 * @author 曾鹏
 */
public class Processor {

    /**
     * 创建一个固定大小为16的线程池
     */
    private static final ExecutorService service = Executors.newFixedThreadPool(16);

    /**
     * 该方法定义了处理读事件的逻辑:
     * 将读事件交给线程池进行处理
     * @param selectionKey
     */
    public void process(SelectionKey selectionKey) {
        /**
         *  Lambda表达式:允许将行为传入到函数中，()->替代了整个匿名类
         *  这段代码虽然没有创建线程，但在实际执行时，其实创建了一个线程对象
         *  {}中存放的就是run方法体中存放的代码,而且因为submit()方法中需要Callable类型的对象
         *  所以，将会默认创建一个Callable类型的对象
         */
        service.submit(() -> {
            System.out.println(Thread.currentThread().getName()+"处理..");
            //获得缓冲区对象
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //获得服务器端channel对象
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            int count = socketChannel.read(buffer);

            //-1则终止连接
            if (count < 0) {
                socketChannel.close();
                selectionKey.cancel();
                System.out.println("{}\t Read ended"+ socketChannel);
                return null;
            } else if(count == 0) {
//                System.out.println(count);
                return null;
            }
            System.out.println("{}\t Read message from"+socketChannel.getRemoteAddress()+" ,"+new String(buffer.array()));

            //写入信息，触发客户端的读事件
            socketChannel.write(ByteBuffer.wrap("123456".getBytes()));

            return null;
        });
    }
}
