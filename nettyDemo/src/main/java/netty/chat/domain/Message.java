package netty.chat.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 类说明: 消息实体
 *
 * @author zengpeng
 */
@Data
public class Message extends BaseMessage implements Serializable {

    /**
     * 消息
     */
    private String message;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 接收时间
     */
    private Date receivedTime;

    /**
     * 发送人ID
     */
    private Long senderId;

    /**
     * 接收人ID
     */
    private Long receiverId;
}
