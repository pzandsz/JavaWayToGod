package demo.network.bio2;

import java.io.*;
import java.net.Socket;

/**
 * 将服务器中的socket交给线程来处理
 * @author 曾鹏
 */
public class BioServerHandler implements Runnable{

    private Socket socket;

    public BioServerHandler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        //BufferedReader对各种Reader提供了缓存功能，这样可以避免多次读取底层IO
        try(BufferedReader bf=new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            PrintWriter pw=new PrintWriter(socket.getOutputStream(),true);

            String message;
            String result;

            while ((message=bf.readLine())!=null){
                System.out.println("服务器端收到信息:"+message);
                result="服务器确认收到信息:"+message;
                //将信息发送到连接(通道)中
                pw.print(result);
            }



        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    private void close(){
        if(socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
