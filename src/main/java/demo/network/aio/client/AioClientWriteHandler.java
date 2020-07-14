package demo.network.aio.client;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AioClientWriteHandler implements CompletionHandler<Integer, ByteBuffer>{

    private AsynchronousSocketChannel clientChannel;

    private CountDownLatch latch;

    public AioClientWriteHandler(AsynchronousSocketChannel clientChannel,
                                 CountDownLatch latch) {
        this.clientChannel = clientChannel;
        this.latch = latch;
    }

    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        if(buffer.hasRemaining()){
            //如果还没写完，继续写
            clientChannel.write(buffer,buffer,this);
        }else{

            //读取数据
            ByteBuffer readBuffer=ByteBuffer.allocate(1024);
            //异步读
            clientChannel.read(readBuffer,readBuffer,
                    new AioClientReadHandler(clientChannel,latch));

        }




    }

    @Override
    public void failed(Throwable exc, ByteBuffer buffer) {
        System.out.println("数据发送失败...");
    }
}
