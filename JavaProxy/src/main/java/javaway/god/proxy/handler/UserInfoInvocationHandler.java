package javaway.god.proxy.handler;

import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 类说明:
 * InvocationHandler的invoke方法: 定义了代理对象调用方法时希望执行的动作，
 *  用于集中处理在动态代理类对象上的方法调用
 *
 * @author zengpeng
 *
 * 基于JDK的动态代理：
 *  1.创建一个实现InvocationHandler接口的类，并自定义invoke方法的实现
 *  2. 被代理的对象必须实现一个接口,JDK通过接口类的class动态的创建代理对象
 *
 * rpc过程：
 *  生产者将接口名称interfaceName,服务提供方的url地址 存储到第三方(本地,zookeeper,redis)
 *  消费者通过接口名称interfaceName,获取对应的服务提供方的url地址
 *  消费者构建入参，通过netty或者http向服务提供方发送请求
 *  生产者服务接收到请求，根据入参进行逻辑处理，并将结果返回给消费者
 *  消费者收到结果并使用
 *
 * 扩展：如何实现 XXXXInvocationHandler 对象的动态创建？
 * 在rpc框架中，InvocationHandler是如果创建的？
 *
 */
@Data
public class UserInfoInvocationHandler implements InvocationHandler {

    Object target;

    public UserInfoInvocationHandler(Object target){
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target,args);
        after();
        return result;
    }

    /**
     * 调用invoke方法之前执行
     */
    private void before(){
        System.out.println("-----before------");
    }

    /**
     * 调用invoke方法之后执行
     */
    private void after(){
        System.out.println("-----after------");
    }
}
