package netty.chat.server.handler;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import netty.chat.domain.Message;
import netty.chat.server.utils.ServerMessageUtil;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 类说明: 聊天消息读取处理器
 *
 * @author zengpeng
 */
public class ChatMessageReadServerHandler<T extends Message> implements ChannelInboundHandler {

    public static ConcurrentHashMap<String, ChannelHandlerContext> channelMap = new ConcurrentHashMap<>();

    /**
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel注册到EventLoop");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel未注册到EventLoop");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel连接成功");

        //将当前连接的channel注册到map中
        channelMap.put(ctx.channel().id().asLongText(),ctx);

        //通知其他连接，当前有新连接建立
        Collection<ChannelHandlerContext> values = channelMap.values();
        Message message = new Message();
        message.setMessage(ctx.channel().remoteAddress().toString() + "加入！");
        values.forEach((val)->{
            ServerMessageUtil.sendMessage(val.channel().id().asLongText(),message);
        });




    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel未连接服务器");
    }

    /**
     * 可以抽象出消息的读取和写入方法，交给子类去具体实现
     * 当前类中直接处理固定的消息对象
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("读取消息");
        //传来的Object对象，即为解码后的消息实体
        T message = (T) msg;
        Gson gson = new Gson();
        System.out.println("消息对象: " + gson.toJson(message));

        //释放该对象在缓存池中的占用,在正式使用时应该充分考虑何时才能清除
        ReferenceCountUtil.release(msg);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("读取结束");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }

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

    }


}
