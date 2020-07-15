package sorket.cookie.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 类说明: 发送http请求
 *
 * @author zengpeng
 */
public class HttpSender {
    private final String URL = "www.csdn.net";
    private final String CONTENT = "GET http://www.csdn.net/  HTTP/1.1\r\nHost: www.csdn.net  Cookie: PREF=ID=80a06da87be9ae3c:U=f7167333e2c3b714:NW=1:TM=1261551909:LM=1261551917:S=ybYcq2wpfefs4V9g; NID=31=ojj8d-IygaEtSxLgaJmqSjVhCspkviJrB6omjamNrSm8lZhKy_yMfO2M4QMRKcH1g0iQv9u-2hfBW7bUFwVh7pGaRUb0RnHcJU37y-FxlRugatx63JLv7CWMD6UB_O_r\r\n\r\n" ;
    private final int PORT = 80 ;

    private final String TEST = "GET http://www.csdn.net/  HTTP/1.1  Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-silverlight, application/x-shockwave-flash, */*  Referer:http://www.google.cn/Accept-Language: zh-cn  Accept-Encoding: gzip, deflate  User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; TheWorld)  Host:www.csdn.net Connection: Keep-Alive  Cookie: PREF=ID=80a06da87be9ae3c:U=f7167333e2c3b714:NW=1:TM=1261551909:LM=1261551917:S=ybYcq2wpfefs4V9g; NID=31=ojj8d-IygaEtSxLgaJmqSjVhCspkviJrB6omjamNrSm8lZhKy_yMfO2M4QMRKcH1g0iQv9u-2hfBW7bUFwVh7pGaRUb0RnHcJU37y-FxlRugatx63JLv7CWMD6UB_O_r\n";
    public void sendHTTP(){
        try {
            //建立TCP/IP链接
            Socket socket = new Socket(URL,PORT);
            OutputStream out = socket.getOutputStream() ;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            //发送数据
            out.write(CONTENT.getBytes());
            int d = -1 ;
            //接收
            while((d=in.read())!=-1){
                //输出到控制台
                System.out.print((char)d);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        HttpSender sender = new HttpSender();
        sender.sendHTTP();
    }
}
