package sorket.cookie.http;

import org.springframework.util.StringUtils;
import sorket.cookie.domain.Request;
import sorket.cookie.domain.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 类说明: http消息解析类
 *
 * @author zengpeng
 */
public class HttpMessageParser {
    /**
     * 根据标准的http协议，解析请求行
     *
     * @param reader
     * @param request
     */
    private static void decodeRequestLine(BufferedReader reader, Request request) throws IOException {
        String s = reader.readLine();
        String[] strs = s.split(" ");
        assert strs.length == 3;
        request.setMethod(strs[0]);
        request.setUri(strs[1]);
        request.setVersion(strs[2]);
    }

    /**
     * 根据标准http协议，解析请求头
     *
     * @param reader
     * @param request
     * @throws IOException
     */
    private static void decodeRequestHeader(BufferedReader reader, Request request) throws IOException {
        Map<String, String> headers = new HashMap<String, String>(16);
        String line = reader.readLine();
        String[] kv;
        while (!"".equals(line)) {
            kv = StringUtils.split(line, ":");
            assert kv.length == 2;
            headers.put(kv[0].trim(), kv[1].trim());
            line = reader.readLine();
        }

        request.setHeaders(headers);
    }

    /**
     * 根据标注http协议，解析正文
     * 在解析请求信息时，需要知道请求的字节长度 contentLen
     * @param reader
     * @param request
     * @throws IOException
     */
    private static void decodeRequestMessage(BufferedReader reader, Request request) throws IOException {
        int contentLen = Integer.parseInt(request.getHeaders().getOrDefault("Content-Length", "0"));
        if (contentLen == 0) {
            // 表示没有message，直接返回
            // 如get/options请求就没有message
            return;
        }

        char[] message = new char[contentLen];
        reader.read(message);
        request.setMessage(new String(message));
    }

    /**
     * http的请求可以分为三部分
     *
     * 第一行为请求行: 即 方法 + URI + 版本
     * 第二部分到一个空行为止，表示请求头
     * 空行
     * 第三部分为接下来所有的，表示发送的内容,message-body；其长度由请求头中的 Content-Length 决定
     *
     * 几个实例如下
     *
     * @param reqStream
     * @return
     */
    public static Request parse2request(InputStream reqStream) throws IOException {
        BufferedReader httpReader = new BufferedReader(new InputStreamReader(reqStream, "UTF-8"));
        Request httpRequest = new Request();
        //读取请求行
        decodeRequestLine(httpReader, httpRequest);
        //读取请求头
        decodeRequestHeader(httpReader, httpRequest);
        //读取请求数据
        decodeRequestMessage(httpReader, httpRequest);
        return httpRequest;
    }

    /**
     * 构建响应头信息
     * @param request
     * @param response
     * @param cookies
     * @return
     */
    public static String buildResponse(Request request, String response,String cookies) {
        Response httpResponse = new Response();
        //设置响应状态行
        httpResponse.setCode(200);
        httpResponse.setStatus("ok");
        httpResponse.setVersion(request.getVersion());

        //设置消息报头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Content-Length", String.valueOf(response.getBytes().length));
        //设置cookie
        headers.put("Set-Cookie",cookies);
        httpResponse.setHeaders(headers);

        httpResponse.setMessage(response);

        StringBuilder builder = new StringBuilder();

        //响应状态行
        buildResponseLine(httpResponse, builder);
        //响应头
        buildResponseHeaders(httpResponse, builder);
        //响应正文
        buildResponseMessage(httpResponse, builder);
        return builder.toString();
    }


    /**
     * 构建响应状态行
     * @param response
     * @param stringBuilder
     */
    private static void buildResponseLine(Response response, StringBuilder stringBuilder) {
        stringBuilder.append(response.getVersion()).append(" ").append(response.getCode()).append(" ")
                .append(response.getStatus()).append("\n");
    }

    /**
     * 构建响应正文
     * @param response
     * @param stringBuilder
     */
    private static void buildResponseHeaders(Response response, StringBuilder stringBuilder) {
        for (Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        stringBuilder.append("\n");
    }

    /**
     * 构建响应正文
     * @param response
     * @param stringBuilder
     */
    private static void buildResponseMessage(Response response, StringBuilder stringBuilder) {
        stringBuilder.append(response.getMessage());
    }


}
