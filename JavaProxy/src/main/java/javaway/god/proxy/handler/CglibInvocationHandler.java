package javaway.god.proxy.handler;

import javaway.god.proxy.inter.CglibService;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * 类说明:cglib动态代理,自定义invoke方法执行
 *
 * @author zengpeng
 */
public class CglibInvocationHandler implements InvocationHandler {

    private CglibService cglibService;

    public CglibInvocationHandler(CglibService cglibService){
        this.cglibService = cglibService;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(cglibService, args);

        after();
        return obj;
    }

    private void before(){
        System.out.println("---------before---------");
    }

    private void after(){
        System.out.println("---------after---------");
    }
}
