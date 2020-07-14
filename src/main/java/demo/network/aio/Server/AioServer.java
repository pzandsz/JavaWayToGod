package demo.network.aio.Server;

/**
 * 基于异步通信I/O的AIO网络模型:服务器端
 * @author 曾鹏
 */
public class AioServer {

    private static AioServerHandler aioServerHandler;

    /**
     * 客户端个数
     */
    public volatile  static long clientCount=0;

    public static void start(){
        if(aioServerHandler!=null){
            return;
        }

        aioServerHandler=new AioServerHandler(8082);
        new Thread(aioServerHandler,"Server").start();
    }

    public static void main(String[] args) {
        AioServer.start();
    }
}
