package demo.network.aio.client;

import java.util.Scanner;

/**
 * AIO客户端的主程序
 * @author 曾鹏
 */
public class AioClient {
    private static AioClientHandler aioClientHandler;

    public static void start(){
        if(aioClientHandler!=null){
            return;
        }

        aioClientHandler=new AioClientHandler("localhost",8082);

        new Thread(aioClientHandler,"Client").start();
    }

    /**
     * 向服务器发送消息
     * @param msg
     * @return
     */
    public static boolean sendMsg(String msg){
        if(msg.equals("q")){
            return false;
        }

        aioClientHandler.sendMessage(msg);
        return true;
    }

    public static void main(String[] args){
        AioClient.start();
        System.out.println("请输入请求信息:");
        Scanner scanner=new Scanner(System.in);

        //如果信息中没有包含q,那么就进入死循环
        while (AioClient.sendMsg(scanner.nextLine())){

        }
    }


}
