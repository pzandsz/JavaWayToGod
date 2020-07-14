package demo.jvm.deencrpt;

import java.io.*;

/**
 * 类说明:自定义类加载器
 *
 * @author 曾鹏
 */
public class CustomClassLoader extends ClassLoader{
    private final String name;
    private String basePath;
    private final static String FILE_EXT=".class";
    public CustomClassLoader(String name){
        super();
        this.name=name;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    private byte[] loadClassData(String name){
        byte[] data=null;

        XorEncrpt xorEncrpt=new XorEncrpt();
        try {
            String tempName=name.replaceAll("\\.","\\\\");
            //解密
            System.out.println(basePath+tempName+FILE_EXT);
            data=xorEncrpt.decrypt(new File(basePath+tempName+FILE_EXT));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * 普通方法加载
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    private byte[] loadSimpleClass(String name) throws IOException {

        //读取文件内容
        String tempName=name.replaceAll("\\.","\\\\");
        FileInputStream in=new FileInputStream(basePath+tempName+FILE_EXT);

        //读取文件，使用该方式，必须先知道classLoader文件的大小，很不灵活，推荐使用FileChannel
        byte[] data=new byte[748];
        in.read(data);

        in.close();
        return data;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        byte[] data=this.loadClassData(name);
        byte[] data= new byte[0];
        try {
            data = this.loadSimpleClass(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(name+","+data+","+0+","+data.length);
        return this.defineClass(name,data,0,data.length);
    }
}
