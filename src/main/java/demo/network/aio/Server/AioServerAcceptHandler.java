package demo.network.aio.Server;


import demo.network.aio.client.AioClientHandler;
import demo.network.aio.client.AioClientReadHandler;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AioServerAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AioServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel channel, AioServerHandler serverHandler) {

        AioServer.clientCount++;
        System.out.println("连接的客户端个数:"+AioServer.clientCount);

        //再次接受新的客户端
        serverHandler.serverChannel.accept(serverHandler,this);

        //读取数据
        ByteBuffer readBuffer=ByteBuffer.allocate(1024);
        //异步读
        channel.read(readBuffer,readBuffer,new AioServerReadHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AioServerHandler serverHandler) {
        exc.printStackTrace();
        serverHandler.latch.countDown();
    }
}
