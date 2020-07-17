package netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 类说明:
 * https://fangjian0423.github.io/2016/08/29/netty-in-action-note2/
 * Channel生命周期
 * channelUnregistered : channel创建后，还未注册到 EventLoop
 * channelRegister : channel注册到了对应的 EventLoop中
 * channelActive : channel处于活跃状态，活跃状态表示已经连接到了远程服务器，现在可以接受和发送数据了
 * channelInactive : channel未连接到远程服务器
 *
 * channelRegistered -> channelActive -> channelInactive -> channelUnregistered
 * @author zengpeng
 */
public class EchoServerHandler implements ChannelInboundHandler {

    /**
     * Channel注册到EventLoopGroup,并可以处理IO请求
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * Channel从EventLoopGroup中被取消连接，并且不能处理任何IO请求
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * Channel已经连接到远程服务器，并且准备好了接受数据(做了什么准备?)
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * Channel还没有连接到远程服务器
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * 有数据读取时触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 覆盖channelRead()事件处理程序方法
        ByteBuf in = (ByteBuf) msg;
        System.out.println(
                "Server received: " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }

    /**
     * channel的读取操作已经完成
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // channelRead()执行完成后，关闭channel连接
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }


    /**
     * 当用户电泳Channel.fireUserEventTriggered方法的时候触发
     * 用户可以传递一个自定义的对象到这个方法里
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }

    /**
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
