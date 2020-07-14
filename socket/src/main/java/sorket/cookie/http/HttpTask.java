package sorket.cookie.http;

import sorket.cookie.domain.Request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

/**
 * 类说明:
 *
 * @author zengpeng
 */
public class HttpTask implements Runnable{
    private Socket socket;

    public HttpTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        if (socket == null) {
            throw new IllegalArgumentException("socket can't be null.");
        }

        try {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream);

            Request httpRequest = HttpMessageParser.parse2request(socket.getInputStream());
            Map<String, String> headers = httpRequest.getHeaders();
            System.out.println("-------------------------------------");
            System.out.println(httpRequest.getMethod());
            headers.forEach((a,b)->{
                if(a.toLowerCase().equals("cookie")){
                    System.out.println(a + "," + b);
                }
            });
            System.out.println("-------------------------------------");

            try {
                // 根据请求结果进行响应，省略返回
                String result = "...";
                String httpRes = HttpMessageParser.buildResponse(httpRequest, result);

                out.print(httpRes);
            } catch (Exception e) {
                String httpRes = HttpMessageParser.buildResponse(httpRequest, e.toString());
                out.print(httpRes);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
