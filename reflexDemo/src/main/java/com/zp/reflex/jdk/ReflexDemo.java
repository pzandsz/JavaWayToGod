package com.zp.reflex.jdk;

import com.zp.reflex.Demo;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类说明: JDK反射 demo
 * 反射作为java的特征之一，他允许运行中的Java程序获取自身的信息，并且可以操作类或对象的内部属性
 *
 * 通过反射，我们可以在运行时获取程序或程序集中每一个类型的成员和成员的信息。程序中一般的对象
 * 都是在编译器就确定下来的，而Java反射机制可以动态的创建对象并调用其属性，这样的对象在编译期
 * 是未知的。
 *
 * Java反射主要提供一下功能:
 *      在运行时判断任意一个对象所属的类
 *      在运行时构建任意一个类的对象
 *      在运行时判断任意一个类所具有的成员变量和方法(通过反射甚至可以调用private方法)
 *      在运行时调用任意一个对象的方法
 *
 * 反射最重要的用途就是开发各种通用的框架
 * @author zengpeng
 */
public class ReflexDemo {
    /**
     * 通过反射获取Class对象
     */
    public void getClassByReflex(){
        //1.使用Class类的forName静态方法,例如JDBC加载驱动就是通过Class.forName(className)来实现的
        //需要注意的是传入的参数是全限定类名
        try {
            Class<?> aClass = Class.forName("com.zp.reflex.Demo");
            System.out.println(aClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2.直接获取某个对象的Class
        Class<Integer> integerClass = int.class;
        Class<Integer> integerClass1 = Integer.class;
        System.out.println(integerClass.getName());
        System.out.println(integerClass1.getName());

        //3.调用某个对象的getClass方法
        Demo demo = new Demo();
        Class<? extends Demo> aClass = demo.getClass();
        System.out.println(aClass.getName());
    }


    /**
     * 判断是否为某个类的实例
     */
    public void judgeClass(){
        //一般通过instanceof关键字来判断是否为某个类的实例
        String str = "str";
        System.out.println(str instanceof String);
        //或者也可以借助反射中Class对象的isInstance方法来判断
        Class<? extends String> aClass = str.getClass();
        System.out.println(aClass.isInstance(str));
        System.out.println(aClass.isInstance(String.class));

        /**
         * instanceof关键字和isInstance方法的区别?
         *
         * clazz.inInstance(obj) 表明这个对象能不能被转化为这个类
         */


    }

    /**
     * 3.创建实例
     * 通过反射来生成对象主要有两种方式
     *      newInterface()
     *      通过Class对象的Constructor对象，在调用Constructor对象的newInterface方法
     */
    public void create() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        //1.直接通过newInterface创建
        Class<String> stringClass = String.class;
        String s = stringClass.newInstance();
        System.out.println(s.getClass().getName());

        //2.通过Constructor间接创建
        Constructor constructor = stringClass.getConstructor(String.class);
        Object dddd = constructor.newInstance("dddd");
        System.out.println(dddd);

        /**
         * 通过Constructor间接创建的好处是可以指定参数值
         */
    }


    /**
     * 获取方法method
     * getDeclaredMethods:方法返回类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法
     *                  ，但不包括继承的方法。
     * getMethods:方法返回某个类的所有公用（public）方法，包括其继承类的公用方法
     *
     * getMethod:方法返回一个特定的方法，其中第一个参数为方法名称，后面的参数为方法的参数对应Class的对象。
     */
    public void methods() throws Exception {
        Class<?> c = ReflexDemo.class;
        Method[] methods = c.getMethods();
        Method[] declaredMethods = c.getDeclaredMethods();
        //获取methodClass类的add方法
        Method method = c.getMethod("create");
        //getMethods()方法获取的所有方法
        System.out.println("getMethods获取的方法："+ method.getName());
        for (Method m : methods) {
            System.out.println(m);
        }
        //getDeclaredMethods()方法获取的所有方法
        System.out.println("getDeclaredMethods获取的方法：");
        for (Method m : declaredMethods){
            System.out.println(m);
        }
    }

    /**
     * 5.获取构造器信息
     * 6.获取类的成员变量信息
     * 7.调用方法
     * 8.利用反射创建数组
     */


    /**
     * Array.newInterface创建数组，其中Array是java.lang.refect.Array类，
     * newArray是一个native方法
     * @throws ClassNotFoundException
     */
    public void testArray() throws ClassNotFoundException {
        Class<?> cls = Class.forName("java.lang.String");
        Object array = Array.newInstance(cls,25);
        //往数组里添加内容
        //Array是java.lang.reflex.Array类
        Array.set(array,0,"hello");
        Array.set(array,1,"Java");
        Array.set(array,2,"fuck");
        Array.set(array,3,"Scala");
        Array.set(array,4,"Clojure");
        //获取某一项的内容
        System.out.println(Array.get(array,3));
    }

    public static void main(String[] args) throws Exception {
        ReflexDemo demo = new ReflexDemo();
//        demo.getClassByReflex();

//        demo.judgeClass();

//        demo.create();

//        demo.methods();

        demo.testArray();
    }

}
