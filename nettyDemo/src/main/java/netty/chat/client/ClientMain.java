package netty.chat.client;

import netty.chat.client.handler.MessageWriteHandle;
import netty.chat.domain.Message;

import java.util.Date;
import java.util.Scanner;

/**
 * 类说明:
 *
 * @author zengpeng
 */
public class ClientMain {

    public static void main(String[] args) throws InterruptedException {
        ChatClient client = new ChatClient();
//
//        Scanner scanner = new Scanner(System.in);
//        String s = scanner.nextLine();
//        Message message = new Message();
//        message.setMessage(s);
//        message.setSendTime(new Date());
//        message.setSenderId(1L);

        client.start("127.0.0.1",8085);

    }
}
