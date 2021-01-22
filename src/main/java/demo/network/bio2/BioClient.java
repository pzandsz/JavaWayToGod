package demo.network.bio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端，可以通过控制台输入信息
 */
public class BioClient {


    public static void main(String[] args) throws IOException {
        //创建一个连接
        Socket socket = new Socket("localhost",8081);

        //在客户端可以输入信息
        System.out.println("请输入请求信息:");

        //启动读事件
        new Thread(new ReadMsg(socket)).start();

        PrintWriter pw=null;
        while (true){
            pw = new PrintWriter(socket.getOutputStream());

            //监听鼠标事件
            pw.println(new Scanner(System.in).next());

            //将数据刷新到连接(通道)中
            pw.flush();
        }

    }

    /**
     * 用于处理读请求
     */
    private static class ReadMsg implements Runnable{

        private Socket socket;

        public ReadMsg(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run() {
            /**
             * 1.获得一个BufferedReader对象
             * 监听Socket中是否有数据，如果数据不为空，则一直等待
             */
            try (BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()))){

                String line=null;

                //如果socket中数据为null,则中止循环,否则就一直等待
                while ((line=br.readLine())!=null){
                    System.out.printf("%s/n",line);
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        private void clear() throws IOException {
            if(socket!=null){
                socket.close();
            }
        }
    }
}
