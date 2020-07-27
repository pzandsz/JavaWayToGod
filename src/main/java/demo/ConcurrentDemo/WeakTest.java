package demo.ConcurrentDemo;

import demo.domain.Demo;

import java.lang.ref.WeakReference;

/**
 * 类说明:
 *
 * @author zengpeng
 */
public class WeakTest {
    public static void main(String[] args) {
        WeakReference<Demo> weakReference = new WeakReference(new Demo());
        System.out.println("gc前: " + weakReference.get());
        //Demo demo = weakReference.get();
        System.gc();



        System.out.println("gc后: " + weakReference.get());
    }
}
