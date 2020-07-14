package javaway.god.proxy;

import javaway.god.proxy.domain.UserInfo;

import java.lang.reflect.Proxy;

import javaway.god.proxy.handler.UserInfoInvocationHandler;
import javaway.god.proxy.inter.UserInfoInterface;

/**
 * 类说明: JDK动态代理
 * 基于接口的动态代理
 * @author zengpeng
 */
public class JdkProxyTest {

    public static void main(String[] args) {

        UserInfo userInfo = new UserInfo();
        ClassLoader classLoader = userInfo.getClass().getClassLoader();
        //基于接口实现的动态代理
        Class[] interfaces = userInfo.getClass().getInterfaces();

        UserInfoInterface proxy = (UserInfoInterface) Proxy.newProxyInstance(classLoader, interfaces, new UserInfoInvocationHandler(userInfo));

        proxy.print();
    }
}
