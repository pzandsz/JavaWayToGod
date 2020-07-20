package netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import netty.chat.domain.Message;
import netty.chat.server.handler.ChatMessageReadServerHandler;
import netty.server.EchoServer;

import java.net.InetSocketAddress;

/**
 * 类说明: 聊天室服务器
 *
 * 服务器只负责读取消息，不进行转发或者回复
 *
 * @author zengpeng
 */
public class ChatServer {

    /**
     * 开启服务器
     * @param port 监听端口号
     */
    public void startServer(int port) throws InterruptedException {
        /**
         * Nio事件循环组
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //添加编码器对象，对象序列化最大长度为1M
                            //设置weakCachingConcurrentResolverMap对类加载器进行缓存 支持多线程并发访问 防止内存溢出
                            ch.pipeline()
                                    .addLast(new ObjectDecoder(1024*1024,
                                            ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new ChatMessageReadServerHandler<Message>());
                        }
                    });

            /**
             * 绑定端口，同步等待连接
             * 服务端启动辅助类配置完成之后，调用他的bind方法监听端口
             * 随后调用sync方法阻塞等待绑定成功
             *
             */
            ChannelFuture f = server.bind().sync();
            System.out.println(EchoServer.class.getName() +
                    " started and listening for connections on " + f.channel().localAddress());

            /**
             * 等待服务端监听端口关闭
             * 阻塞等待
             */
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();

        }
    }
}
