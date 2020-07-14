package demo.network.nio.detail;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ServerSocketChannelTest {

    private int size = 1024;
    private ServerSocketChannel socketChannel;
    private ByteBuffer byteBuffer;
    private Selector selector;
    private final int port = 8998;
    private int remoteClientNum=0;

    public ServerSocketChannelTest() {
        try {
            initChannel();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void initChannel() throws Exception {
        //1.创建ServerSocketChannel
        socketChannel = ServerSocketChannel.open();
        //2.修改为非阻塞模式
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(port));
        System.out.println("listener on port:" + port);
        //创建选择器
        selector = Selector.open();
        //3.将通道channel与选择器selector绑定
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //输出一下当前的SelectionKey对象
        System.out.println("是否打开"+socketChannel.isOpen());
        System.out.println("是否阻塞"+socketChannel.isBlocking());
        System.out.println("是否注册"+socketChannel.isRegistered());

        //缓冲去
        byteBuffer = ByteBuffer.allocateDirect(size);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
    }

    private void listener() throws Exception {
        while (true) {
            //获得准备好进行I/O操作的通道数量
            int n = selector.select();
            if (n == 0) {
                continue;
            }
            //遍历selectionKey
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = ite.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();

                    registerChannel(selector, channel, SelectionKey.OP_READ);
                    remoteClientNum++;
                    System.out.println("online client num="+remoteClientNum);
                    replyClient(channel);
                }
                //a channel is ready for reading
                if (key.isReadable()) {
                    readDataFromSocket(key);
                }

                ite.remove();//must
            }

        }
    }

    protected void readDataFromSocket(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        byteBuffer.clear();
        while ((count = socketChannel.read(byteBuffer)) > 0) {
            byteBuffer.flip(); // Make buffer readable
            // Send the data; don't assume it goes all at once
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }
            byteBuffer.clear(); // Empty buffer
        }
        if (count < 0) {
            socketChannel.close();
        }
    }

    /**
     * 向客户端发送信息
     * @param channel
     * @throws IOException
     */
    private void replyClient(SocketChannel channel) throws IOException {
        byteBuffer.clear();
        byteBuffer.put("hello client!\r\n".getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
    }

    /**
     * 注册：将事件绑定到监听器,并指定监听的事件
     * @param selector
     * @param channel
     * @param ops :监听的事件
     * @throws Exception
     */
    private void registerChannel(Selector selector, SocketChannel channel, int ops) throws Exception {
        if (channel == null) {
            return;
        }
        channel.configureBlocking(false);
        channel.register(selector, ops);
    }


    public static void main(String[] args) {
        try {
            new ServerSocketChannelTest().listener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

