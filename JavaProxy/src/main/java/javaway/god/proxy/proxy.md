# 基于JDK的动态代理
1.代理的对象需要实现一个接口，因为JDK动态代理是基于接口实现的。

2.对于代理方法的执行，可以通过重写invoke方法来进行一些额外的处理。

# 基于Cglib的的动态代理
1.代理的对象无需实现接口，Cglib动态代理是基于继承实现的。

2.和基于JDK动态代理一样，如果想要自定义方法的执行流程，可以通过重写InvocationHandler的invoke方法来实现，
但是cglib的InvocationHandler实现了Callable接口，这也就意味着，在cglib中,invoke的执行时异步的。

# RPC中的动态代理
rpc的执行逻辑:
```$xslt
1.生产者将接口名称interfaceName,服务提供方的url地址 存储到第三方(本地,zookeeper,redis)
2.消费者通过接口名称interfaceName,获取对应的服务提供方的url地址
3.消费者构建入参，通过netty或者http向服务提供方发送请求
4.生产者服务接收到请求，根据入参进行逻辑处理，并将结果返回给消费者
5.消费者收到结果并使用
```
从rpc的执行流程来看，我们可以分析出需要代理的流程是通过netty将请求信息
发送到服务提供方并获取返回结果的流程。
