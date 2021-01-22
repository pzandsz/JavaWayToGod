package demo.network.bio2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BioServer {

    private static ServerSocket serverSocket;
    /**
     * 创建一个线程池
     */
    private static ExecutorService threadPool= Executors.newFixedThreadPool(5);

    private static void start() throws IOException {
        serverSocket = new ServerSocket(8081);
        System.out.println("服务已启动，端口号为:"+8081);

        //死循环，轮询
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("有新的客户端请求连接...");

            //根据socket创建一个服务器端的任务
            BioServerHandler handler = new BioServerHandler(socket);
            //将任务交给线程池处理
            threadPool.execute(handler);
        }

    }

    public static void main(String[] args) throws IOException {
        BioServer.start();
    }

}
