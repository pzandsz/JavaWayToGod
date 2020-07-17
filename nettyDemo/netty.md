基于Java提供的对象输入/输出流ObjectInputStream和ObjectOutputStream,
可以直接把对象转化为可存储的字节数组写入文件，也可以传输到网络上。基于
JDK默认的序列化机制可以避免手动的操作底层的字节数组。


java序列化的目的:
    网络传输
    对象持久化
    
在netty中，在进行网络传输的时，发送方需要把被传输的Java对象编码为字节
数组或者ByteBuffer对象。读取方需要将读到的字节数组或者ByteBuffer对象
转换为发送时的Java对象。这就成为java对象编解码技术

java序列化是java对象编解码技术的一种，由于他的种种缺陷，衍生出了多种
编解码编解码技术和框架

java序列化的缺点:
```$xslt
1. 无法跨语言，Java序列化是java语言内部的私有协议，其他语言并不支持，
这也就意味着对于java序列化后的字节数组，别的语言无法进行反序列化
    
2.序列化后的码流太大
    
3.序列化性能太低

```

编解码框架的评价标准
```$xslt
1.是否支持跨语言，支持的语言种类是否丰富；

2.编码后的码流大小：

3.编解码的性能；

4.类库是否小巧，API使用是否方便：

5.使用者需要手工开发的工作量和难度。
```
   
主流的编解码框架
```$xslt
Protobuf,全称GoogleProtocolBuffers，它由谷歌开源而来，
在谷歌内部久经考验。它将数据结构以.proto文件进行描述，
通过代码生成工具可以生成对应数据结构的POJO对象和
Protobuf相关的方法和属性。
    1.结构化数据存储格式（XML,JSON等〉：
    2.高效的编解码性能：
    3.语言无关、平台无关、扩展性好；
    
    4.官方支持Java、C＋＋和Python三种语言

Facebook的Thrift

JBossMarshalling介绍

hessian
```

Netty自带编解码工具
```$xslt
io.netty.handler.codec.MessageToByteEncoder和
io.netty.handler.codec.ByteToMessageDecoder接口

Codec   : 编解码器
Decoder : 解码器
Encoder : 编码器

https://blog.csdn.net/u014401141/article/details/79224789
```