package demo.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明: 测试jvm的监控界面
 *
 * @author 曾鹏
 */
public class JConsoleTest {

    public static void main(String[] args) throws InterruptedException {
        List<JConsoleTest> tests=new ArrayList<>();

        for (int i=0;i<1000;i++){
            Thread.sleep(100);
            tests.add(new JConsoleTest());
        }

        for (int i=1000-1;i>=0;i--){
            tests.remove(i);
        }
    }
}
