package demo.network.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerTask implements Runnable{
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public ServerTask(Socket socket) throws IOException {
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            /**
             * 获得socket的输入输出流对象
             */
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            /**
             * 创建一个字节数组
             */
            byte[] buffer = new byte[1024];
            int result = inputStream.read(buffer);

            System.out.println("线程:" + Thread.currentThread().getName() + "开始处理任务");


            String str = new String(buffer, "utf-8");
            System.out.println("客户端传来:" + str);


            String server = new String("from server：" + str);

            Charset cs = Charset.forName("utf-8");

            //休眠0.1秒
            TimeUnit.MILLISECONDS.sleep(100);

            /**
             * 服务器端的响应
             */
            byte[] bytes = server.getBytes(cs);
            outputStream.write(bytes);
            outputStream.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
