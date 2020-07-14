package demo.jvm.deencrpt;

import java.io.*;

/**
 * 类说明:加密和解密的服务类
 *
 * @author 曾鹏
 */
public class XorEncrpt {

    /**
     * 异或运算，一个书经过两次异或运算还是自己
     * @param in
     * @param out
     */
    public void xor(InputStream in, OutputStream out) throws IOException {
        int ch;
        while (-1!=(ch=in.read())){
            ch=ch^0xff;
            out.write(ch);
        }
    }

    /**
     * 加密方式(流的方式)，解密后重新写入
     * @param src
     * @param des
     * @throws IOException
     */
    public void encrypt(File src,File des) throws IOException {
        InputStream in=new FileInputStream(src);
        OutputStream out=new FileOutputStream(des);

        xor(in,out);
        in.close();
        out.close();
    }

    /**
     * 解密方法，返回解密后的二进制数组
     * @param src
     * @return
     */
    public byte[] decrypt(File src) throws IOException {
        InputStream in=new FileInputStream(src);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();

        xor(in,bos);

        byte[] bytes = bos.toByteArray();
        in.close();
        return bytes;
    }

    public static void main(String[] args) throws IOException {
        File src=new File("D:\\JavaWayToGod\\out\\production\\JavaWayToGod\\demo\\jvm\\deencrpt\\DemoUser.class");
        File dest=new File("D:\\JavaWayToGod\\out\\production\\JavaWayToGod\\demo\\jvm\\deencrpt\\DemoUserTest.class");

        XorEncrpt xorEncrpt=new XorEncrpt();
        //加密
        xorEncrpt.encrypt(src,dest);

        System.out.println("加密完成");

    }
}
