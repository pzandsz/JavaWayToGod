package javaway.god.proxy;

import javaway.god.proxy.handler.CglibInvocationHandler;
import javaway.god.proxy.inter.CglibService;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 类说明:Cglib动态代理
 *
 * @author zengpeng
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        //1.创建目标对象
        CglibService cglibService = new CglibService();

        //2.创建代理对象
        //2.1创建增强器对象
        Enhancer e = new Enhancer();
        //2.2设置增强器的类加载器
        e.setClassLoader(cglibService.getClass().getClassLoader());
        //2.3设置代理对象的父类类型
        e.setSuperclass(cglibService.getClass());
        //2.4设置回调函数
        e.setCallback(new CglibInvocationHandler(cglibService));

        //2.5创建代理对象
        CglibService proxy = (CglibService) e.create();

        //3.方法调用
        proxy.print("张三");
    }
}
