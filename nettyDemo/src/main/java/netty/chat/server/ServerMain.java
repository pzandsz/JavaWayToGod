package netty.chat.server;

/**
 * 类说明:
 *
 * @author zengpeng
 */
public class ServerMain {
    public static void main(String[] args) throws InterruptedException {
        ChatServer server = new ChatServer();
        server.startServer(8085);

    }
}
