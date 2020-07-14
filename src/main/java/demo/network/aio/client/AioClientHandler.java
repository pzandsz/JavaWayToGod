package demo.network.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AioClientHandler implements CompletionHandler<Void,AioClientHandler> ,Runnable{

    /**
     * 客户端异步操作的socket:AsynchronousSocketChannel
     * 负责接受连接
     */
    private AsynchronousSocketChannel clientChannel;

    private String hostname;
    private int port;

    /**
     * 使用CountDownLatch是为了不让客户端过早的退出
     */
    private CountDownLatch latch;


    public AioClientHandler(String hostname,int port){
        this.hostname=hostname;
        this.port=port;
        try {
            //创建一个实际的客户端通道
            clientChannel=AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 类似于ajax中的success
     * @param result
     * @param attachment
     */
    @Override
    public void completed(Void result, AioClientHandler attachment) {
        System.out.println("成功建立连接...");
    }

    @Override
    public void failed(Throwable exc, AioClientHandler attachment) {

        System.out.println("建立连接失败...");
    }

    @Override
    public void run() {
        //创建一个CountDownLatch实例，等待count数量为1
        latch=new CountDownLatch(1);

        clientChannel.connect(new InetSocketAddress(hostname,port),
                this,this);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器端发送消息的方法
     * 注意：在使用aio获得nio对数据进行处理时，都要通过Buffer实现一层缓冲
     */
    public void sendMessage(String message){
        //为了把msg变为可以在网络传输的格式
        byte[] bytes=message.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        //flip(),切换成读模式
        writeBuffer.flip();

        //AsynchronousSocketChannel的写操作,异步的写
        clientChannel.write(writeBuffer,writeBuffer,new AioClientWriteHandler(clientChannel,latch));

    }
}
