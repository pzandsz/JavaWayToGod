package netty.chat.server.utils;

import io.netty.channel.ChannelHandlerContext;
import netty.chat.domain.Message;
import netty.chat.server.handler.ChatMessageReadServerHandler;
import org.springframework.util.Assert;

/**
 * 类说明: 服务器端消息工具
 *
 * @author zengpeng
 */
public class ServerMessageUtil {

    public static void sendMessage(String channelId, Message message){
        Assert.notNull(channelId,"channelId is null");

        ChannelHandlerContext channelHandlerContext = ChatMessageReadServerHandler.channelMap.get(channelId);
        if(channelHandlerContext == null){
            System.out.println("该连接不存在!");
            return;
        }

        channelHandlerContext.writeAndFlush(message);
    }
}
