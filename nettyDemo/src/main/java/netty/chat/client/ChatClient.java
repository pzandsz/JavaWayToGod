package netty.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import netty.chat.client.handler.MessageWriteHandle;
import netty.chat.client.utils.MessageUtil;
import netty.chat.domain.Message;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Scanner;

/**
 * 类说明: 客户端
 *
 * @author zengpeng
 */
public class ChatClient {


    public void start(String domain , int port ) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(domain, port))
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {

                            ch.pipeline()
                                    .addLast(new ObjectDecoder(1024*1024,
                                            ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new MessageWriteHandle());

                        }
                    });


            /**
             * 发起异步连接操作
             * 客户端启动辅助类设置完成之后，调用connect方法发起异步连接
             * 然后调用同步方法等待连接
             */
            ChannelFuture f = bootstrap.connect().sync();

            String id = bootstrap.connect().channel().id().asLongText();
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + ",另起线程监听console输入!");
                Scanner in = new Scanner(System.in);
                while (true){
                    String next = in.next();
                    if(next.equals("exit")){
                        break;
                    }

                    Message message = new Message();
                    message.setSenderId(System.currentTimeMillis());
                    message.setSendTime(new Date());
                    message.setReceiverId(1L);
                    message.setMessage(next);

                    MessageUtil.sendMessage(id,message);
                }
            }).start();

            /**
             * 等待客户端连接关闭之后，客户端主函数退出
             */
            f.channel().closeFuture().sync();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //优雅的退出，释放NIO线程组
            //在退出之前，释放NIO线程组的资源
            group.shutdownGracefully().sync();
        }

    }

}
