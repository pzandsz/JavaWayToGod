package demo.network.bio;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * BIO模型:在内核将数据准备好之前，系统调用会一直等待所有套接字（socket），默认是阻塞方式
 * 1.应用进程recvfrom,系统调用
 * 2.内核无数据准备好，线程将阻塞等待内核将数据准备好
 * 3.内核将数据准备好
 * 4.复制数据报，数据从内核空间复制到用户空间
 * 5.复制完成，返回数据报
 *
 * 我们Socket调用的read方法，实际上会调用一个native的read方法，最终会调用到c语言中的recvfrom方法
 *
 * 我们的代码想要读取数据时就会调用recvfrom方法，而recvfrom会通知os来执行，os就会判断数据报是否准备好，当数据包准备好之后，
 * os会将数据从内核空间拷贝到用户空间（因为用户程序只能读取到用户空间的内存，无法直接获取到内核空间的内存）；
 * 拷贝完成之后，socket.read()会解除阻塞，并得到read的结果。
 *
 *
 * BIO的阻塞，阻塞在两个地方：
 * 1.os等待数据准备好
 * 2.将数据从内核空间拷贝到用户空间
 *
 * @author 曾鹏
 */
public final class Server {

    /**
     * 缺省值为8081
     */
    private static int PORT = 8081;

    /**
     * 创建一个线程池:核心线程数为8,最大线程数为14,线空闲时间为3秒
     * 阻塞队列为有界数组队列，容量为10,拒绝策略使用默认(抛出异常)
     */
    private ExecutorService threadPoolExecutor = new ThreadPoolExecutor(8,14,3,TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(100));

    /**
     * 创建一个服务器实例对象
     */
    private static Server server = new Server();


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
        ServerSocket server = new ServerSocket(PORT);

        //死循环
        while (true){
            System.out.println("loop：如无连接则被阻塞");
            Socket socket = server.accept();

            //根据socket创建一个任务
            ServerTask task = new ServerTask(socket);
            //交给线程池执行
            threadPoolExecutor.execute(task);

        }
    }



    public void setPort(int port){
        Server.PORT=port;
    }
}
