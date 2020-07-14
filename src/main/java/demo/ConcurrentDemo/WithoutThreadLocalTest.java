package demo.ConcurrentDemo;

import sun.awt.windows.ThemeReader;

public class WithoutThreadLocalTest {
    static class TestTask implements Runnable{
        Person person;
        public TestTask(Person person){
            this.person=person;
        }

        @Override
        public void run() {

            person.setAge(15);
            person.setName(person.getName()+System.currentTimeMillis());
            System.out.println(Thread.currentThread().getName()+"线程:"+person.toString());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Person person=new Person("李四",54);


        Thread threadA = new Thread(new TestTask(person));
        Thread.sleep(1000);
        Thread threadB = new Thread(new TestTask(person));

        threadA.start();
        threadB.start();

    }
}
