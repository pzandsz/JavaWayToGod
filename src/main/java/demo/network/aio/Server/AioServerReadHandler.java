package demo.network.aio.Server;

import demo.network.aio.client.AioClientReadHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;


public class AioServerReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel clientChannel;


    public AioServerReadHandler(AsynchronousSocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    /**
     * 读到消息后的处理
     * @param result
     * @param buffer
     */
    @Override
    public void completed(Integer result, ByteBuffer buffer) {

        //如果条件成立，说明客户端主动终止了TCP套接字,只是服务器就可以停止了
        if(result==-1){
            try {
                clientChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        //切换为读操作
        buffer.flip();
        byte[] message=new byte[buffer.remaining()];
        buffer.get(message);

        System.out.println(result);
        try {
            String msg=new String(message,"UTF-8");
            System.out.println("服务器接收到信息:"+message);
            String responseStr="服务器端已收到信息:"+message;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


    private void doWrite(String result){
        byte[] bytes=result.getBytes();
        ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();

        clientChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if (buffer.hasRemaining()){
                    clientChannel.write(buffer,buffer,this);
                }else {
                    ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                    clientChannel.read(readBuffer,readBuffer,new AioServerReadHandler(clientChannel));
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

                try {
                    clientChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {

    }
}
