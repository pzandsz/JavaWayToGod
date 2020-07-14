package demo.network.aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * 读处理
 * @author 曾鹏
 */
public class AioClientReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private CountDownLatch latch;
    private AsynchronousSocketChannel clientChannel;


    public AioClientReadHandler(AsynchronousSocketChannel clientChannel,
                                 CountDownLatch latch) {
        this.clientChannel = clientChannel;
        this.latch = latch;
    }


    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        //切换为读操作
        buffer.flip();
        byte[] bytes=new byte[buffer.remaining()];
        String message;
        try {
            message=new String(bytes,"UTF-8");
            System.out.println("客户端收到数据:"+message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void failed(Throwable exc, ByteBuffer buffer) {

        System.err.println("数据读取失败...");
        try {
            clientChannel.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
