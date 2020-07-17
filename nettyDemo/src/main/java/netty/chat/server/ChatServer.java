package netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.chat.server.handler.ChatServerHandler;

import java.net.InetSocketAddress;

/**
 * 类说明: 聊天室服务器
 *
 * @author zengpeng
 */
public class ChatServer {

    /**
     * 开启服务器
     * @param port 监听端口号
     */
    public void startServer(int port){
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
                            //添加编码，解码handler
                            ch.pipeline().addLast(new ChatServerHandler());
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}
