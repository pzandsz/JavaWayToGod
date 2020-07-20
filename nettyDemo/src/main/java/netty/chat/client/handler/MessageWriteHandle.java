package netty.chat.client.handler;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import netty.chat.domain.Message;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 类说明:
 * 在该实验中，消息对象是在创建之前就建立好了的
 * 并且在连接成功后就将消息发送到服务器中
 *
 * 无法在外部直接触发sendMessage的原因是因为连接的线程会阻塞，导致其他线程的动作在等待
 * (解决思路:同步等待改为异步)
 *
 * 如何灵活的发送消息，接受消息，还需要深入研究，如何设置连接时长
 *  思路一: 设置一个消息Map,以用户ID作为key
 *  思路二: 若为长连接，异步发送消息
 * @author zengpeng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageWriteHandle implements ChannelInboundHandler {

    private Message message;

    /**
     * 以channel的id为key,ChannelHandlerContext为value
     */
    public static ConcurrentHashMap<String,ChannelHandlerContext> contextMap = new ConcurrentHashMap<>();



    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接建立成功");
        //将当前channel的channelHandlerContext注册到map中
        contextMap.put(ctx.channel().id().asLongText(),ctx);
        //连接成功后发送消息
//        setMessage(ctx , message);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        Gson gson = new Gson();
        System.out.println("消息对象: " + gson.toJson(message));

        //释放该对象在缓存池中的占用,在正式使用时应该充分考虑何时才能清除
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

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
