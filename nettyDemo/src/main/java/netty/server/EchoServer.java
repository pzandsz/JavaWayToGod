package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.handler.EchoServerHandler;

import java.net.InetSocketAddress;

/**
 * 类说明: 入门demo 服务器
 *
 * @author zengpeng
 */
public class EchoServer {
    /**
     * 服务器端监听的端口号
     */
    public final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        EchoServer server = new EchoServer(8089);
        /**
         * Nio事件循环组
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //ServerBootstrap是一个用于设置服务器的引导类
            ServerBootstrap b = new ServerBootstrap();

            //绑定事件处理管道
            // 使用NioServerSocketChannel类，用于实例化新的通道以接受传入连接
            b.group(group) .channel(NioServerSocketChannel.class)
                    // 设置服务器监听端口号
                    .localAddress(new InetSocketAddress(server.port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 添加请求处理
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            //绑定到端口和启动服务器  同步
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() +
                    " started and listening for connections on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
