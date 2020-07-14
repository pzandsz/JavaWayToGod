package demo.network.aio.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * 处理连接的异步对象
 */
public class AioServerHandler implements CompletionHandler<Void,Void>,Runnable {

    /**
     * 客户端异步操作的socket:AsynchronousSocketChannel
     * 负责接受连接
     */
    public AsynchronousServerSocketChannel serverChannel;

    private int port;

    /**
     * 使用CountDownLatch是为了不让服务器端过早的退出
     */
    public CountDownLatch latch;


    public  AioServerHandler(int port){
        this.port=port;
        try {
            serverChannel=AsynchronousServerSocketChannel.open();

            /**
             * 监听端口
             */
            serverChannel.bind(new InetSocketAddress(port));

            System.out.println("服务器端已启动....");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, Void attachment) {

    }

    @Override
    public void failed(Throwable exc, Void attachment) {

    }

    @Override
    public void run() {
//创建一个CountDownLatch实例，等待count数量为1
        latch=new CountDownLatch(1);

        //用于接受客户端请求
        serverChannel.accept(this,new AioServerAcceptHandler());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
