package sorket.cookie.domain;

import lombok.Data;

import java.util.Map;

/**
 * 类说明: 请求对象
 *
 * @author zengpeng
 */
@Data
public class Request {
    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求的uri
     */
    private String uri;

    /**
     * http的版本
     */
    private String version;

    /**
     * 请求头
     */
    private Map<String,String> headers;

    /**
     * 请求消息相关
     */
    private String message;
}
