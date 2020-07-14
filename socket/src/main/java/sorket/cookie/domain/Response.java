package sorket.cookie.domain;

import lombok.Data;

import java.util.Map;

/**
 * 类说明: 响应
 *
 * @author zengpeng
 */
@Data
public class Response {
    private String version;
    private int code;
    private String status;

    private Map<String, String> headers;

    private String message;

}
