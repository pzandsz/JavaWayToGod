package demo.ConcurrentDemo.pool;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * 模拟数据库驱动方提供的实现了java.sql.Connection的接口的实现类
 * @author 曾鹏
 */
public class ConnectionDriver {
    /**
     * 类说明:实现InvocationHandler接口
     * 动态代理的处理器类
     */
    static class ConnectionHandler implements InvocationHandler {

        @Override
        public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
            if(method.getName().equals("commit")){

                /**
                 * 对Thread.sleep()的封装，在其基础上添加了时间单位转化和验证
                 * 下述代码是线程休眠100ms
                 */
                TimeUnit.MILLISECONDS.sleep(100);
//                System.out.println("\n"+System.currentTimeMillis()+"监控到commit方法");
            }
            return null;
        }
    }


    /**
     * 创建一个数据库连接
     * @return
     */
    public static final Connection createConnection(){
        /**
         * Proxy.newProxyInstance(classLoader,class[],connectionHandler)方法，
         * 会返回一个指定接口的代理类的实例，并将方法调用分派给指定的调用处理程序connectionHandler
         */
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class<?>[]{Connection.class},new ConnectionHandler());
    }
}
