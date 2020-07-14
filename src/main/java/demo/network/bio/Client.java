package demo.network.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

public class Client {
    public static void main(String[] args) throws IOException {
        long begin = System.currentTimeMillis();

        OutputStream outputStream = null;
        InputStream inputStream = null;

        for (int i = 0; i < 1000; i++) {
            Socket socket = new Socket("localhost", 8081);
            outputStream = socket.getOutputStream();

            String str = "client -" + i;

            Charset charset = Charset.forName("utf-8");

            //通过buffer缓冲区进行读写
            byte[] buffer = str.getBytes(charset);
            outputStream.write(buffer);
            outputStream.flush();

            /**
             * 接收服务器端的响应信息
             */
            inputStream = socket.getInputStream();
            byte[] fromServer = new byte[1024];
            int ren = inputStream.read(fromServer);
            int len = fromServer.length;
            String stssr = new String(fromServer, "utf-8");
            System.out.println(" str:" + stssr);
            outputStream.close();
            inputStream.close();
            inputStream = null;
            outputStream = null;
            socket.close();
            socket = null;
            fromServer = null;
        }
        long end = System.currentTimeMillis();
        long total = end - begin;
        System.out.println("Client-total:" + total);
    }

}
