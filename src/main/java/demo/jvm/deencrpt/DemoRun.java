package demo.jvm.deencrpt;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class DemoRun {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //new出自己定义的类加载器
        CustomClassLoader customClassLoader=new CustomClassLoader("My ClassLoader");
        //设置类加载路径
        customClassLoader.setBasePath("D:\\JavaWayToGod\\out\\production\\JavaWayToGod\\");

        Class<?> clazz=customClassLoader.findClass("demo.jvm.deencrpt.DemoUser");
        System.out.println(clazz.getClassLoader());

        Object o=clazz.newInstance();
        System.out.println(o);
    }
}
