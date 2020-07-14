package demo.ConcurrentDemo.ForkJoin;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 遍历指定目录中的文件
 * 说明:因为遍历不需要返回值，所以该类继承至RecursiveAction
 * @author 曾鹏
 */
public class FindDirFiles extends RecursiveAction {

    private File path;

    public FindDirFiles(File path){
        this.path=path;
    }


    @Override
    protected void compute() {

        List<FindDirFiles> subTasks=new ArrayList<>();
        //获得该文件的子文件
        File[] files=path.listFiles();

        if(files!=null){
            for (File file: files) {
                //判断是否为目录
                if(file.isDirectory()){
                    //对每一个子目录都新建一个子任务
                    subTasks.add(new FindDirFiles(file));
                }else {
                    //遇到文件，检查是否为txt文件
                    if(file.getAbsolutePath().endsWith("txt")){
                        System.out.println("文件:"+file.getAbsolutePath());
                    }

                }
            }
            if(!subTasks.isEmpty()){
                //在当前的ForkJoinPool实例调度总任务
                for (FindDirFiles subTask:invokeAll(subTasks)){
                    subTask.join();
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            //用一个ForkJoinPool实例来调度总任务
            ForkJoinPool pool=new ForkJoinPool();
            FindDirFiles findTask=new FindDirFiles(new File("D:/"));

            //没有返回值,使用execute方法执行
            pool.execute(findTask);

            Thread.sleep(1);
            int otherWork=0;
            for (int i = 0; i < 100; i++) {
                otherWork+=i;
            }
            System.out.println("Main Thread does sth......,otherWork="+otherWork);
            findTask.join();
            System.out.println("Task end!");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
