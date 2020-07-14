package demo.network.bio;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * BIO模型:服务器端
 * @author 曾鹏
 */
public final class Server {

    /**
     * 缺省值为8081
     */
    private static int PORT=8081;

    /**
     * 创建一个线程池:核心线程数为8,最大线程数为14,线空闲时间为3秒
     * 阻塞队列为有界数组队列，容量为10,拒绝策略使用默认(抛出异常)
     */
    private ExecutorService threadPoolExecutor= new ThreadPoolExecutor(8,14,3,TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(100));

    /**
     * 创建一个服务器实例对象
     */
    private static Server server=new Server();


    public static void start() throws IOException {
        server.doStart();
    }

    /**
     * 真正启动服务器的方法
     */
    private void doStart() throws IOException {
        System.out.println("服务器启动....");
        /**
         * 创建一个Socket套接字，监听8081端口
         * BIO同步阻塞，等待客户端连接
         */
        ServerSocket server=new ServerSocket(PORT);

        while (true){
            Socket socket = server.accept();

            /**
             * 循环等待
             */
            //根据socket创建一个任务
            ServerTask task=new ServerTask(socket);

            //交给线程池执行
            threadPoolExecutor.execute(task);

        }
    }



    public void setPort(int port){
        Server.PORT=port;
    }
}
