package netty.chat.client.utils;

import io.netty.channel.ChannelHandlerContext;
import netty.chat.client.handler.MessageWriteHandle;
import netty.chat.domain.Message;

/**
 * 类说明: 消息发送,读取工具
 *
 * @author zengpeng
 */
public class MessageUtil {
    public static void sendMessage(String channelId, Message message){
        ChannelHandlerContext ctx = MessageWriteHandle.contextMap.get(channelId);
        if(ctx == null){
            System.out.println("该连接已经关闭!");
            return ;
        }

        //向服务端发送信息
        ctx.writeAndFlush(message);
    }

}
