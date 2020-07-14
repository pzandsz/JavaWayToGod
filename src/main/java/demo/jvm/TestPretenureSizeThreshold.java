package demo.jvm;

/**
 * 类说明:
 *
 * @author 曾鹏
 */
public class TestPretenureSizeThreshold {
    private static final int _1MB = 1024 * 1024;

    /**
     * VM 参数：
     * -verbose:gc
     * -Xms20M -Xmx20M -Xmn10M
     * -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:+UseSerialGC  -XX:PretenureSizeThreshold=3145728
     */
    public static void main(String[] args) {
        byte[] allocation;
        allocation = new byte[4 * _1MB];
    }

}
