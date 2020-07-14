package demo.network.nio2;

import java.nio.IntBuffer;

/**
 * 类说明:测试缓冲区对象
 *
 * @author 曾鹏
 */
public class NioTest {

    public static void main(String[] args) {
        IntBuffer intBuffer=IntBuffer.allocate(12);
        for (int i=0;i<10;i++){
            intBuffer.put(i);
        }

        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
