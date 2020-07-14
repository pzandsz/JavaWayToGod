package demo.ConcurrentDemo.CommenClass;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * 现有一场田径比赛，共有8个选手参赛。他们编号分别为： A,B,C,D,E,F,G,H；
 * 赛事观察员分别在开始后   20s和30s拍下两张照片，各个选手的排名情况如下，
 * 20s：F,A,H,G,C,B,E,D；30s：G,H,A,C,F,D,B,E；请你写出一个程序，
 * 模拟出这些参赛选手在20-30s这10s的内排名变化，每1s显示一次排名。
 *
 */
public class Athletics {
    /**
     * 信号枪
     */
    static final CountDownLatch START_GUN = new CountDownLatch(1);
    static final LocalDateTime DATE_TIME = LocalDateTime.now();
    static volatile boolean over = false;
    static long startTime;
    static final int PICTURE_TIME = 20;
    static final int PICTURE_TIME2 = 30;

    public static void main(String[] args) throws InterruptedException {
        int num = 8;
        //8位参赛选手
        CountDownLatch latch = new CountDownLatch(num);
        List<Athlete> athletes = initAthletes(num, latch);
        athletes.stream().forEach(athlete -> athlete.start());
        System.out.println("裁判等待选手就位");
        Computer computer = new Computer(athletes);
        computer.start();
        latch.await();
        System.out.println(DATE_TIME + "时裁判开枪");
        startTime = Instant.now().toEpochMilli();
        START_GUN.countDown();
        computer.join();
        computer.showPhotos();
    }

    private static List<Athlete> initAthletes(int num, CountDownLatch latch) {
        return IntStream.range(0, num).mapToObj(i -> new Athlete(String.valueOf((char) ('A' + i)), latch)).collect(Collectors.toList());
    }

    static class Computer extends Thread {
        List<Picture<Athlete>> pictures = new ArrayList<>();

        static class Picture<E> {
            final List<E> pictureItems;
            final long timestamp;

            public Picture(List<E> items) {
                this.pictureItems = Collections.unmodifiableList(items);
                ;
                this.timestamp = Instant.now().toEpochMilli();
            }

            public void show() {
                pictureItems.stream().forEach(item -> System.out.println(item));
            }

            @Override
            public String toString() {
                return "照片拍摄于" + LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()) +
                        ":" + pictureItems +
                        '}';
            }
        }

        final List<Athlete> athletes;

        public Computer(List<Athlete> athletes) {
            this.athletes = Collections.unmodifiableList(athletes);
        }

        public List<Athlete> compute() {
            return athletes.stream().map(new Function<Athlete, Athlete>() {
                @Override
                public Athlete apply(Athlete athlete) {
                    try {
                        //拍照，返回运动员的成绩快照
                        return (Athlete) athlete.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    throw new RuntimeException("计算机故障");
                }
            }).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }

        @Override
        public void run() {
            try {
                System.out.println("电脑已启动");
                START_GUN.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\n电脑开始工作");
            sleepThenTakeFirstPhoto();
            while (!over && !isInterrupted()) {
                System.out.println();
                long currentSecond = (Instant.now().toEpochMilli() - startTime) / 1000 + 1;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Athlete> sortedAthletes = compute();
                System.out.print(currentSecond + "秒: ");
                sortedAthletes.stream().forEach(athlete -> System.out.print(athlete.getName() + " " + athlete.curLength + " "));
                if (currentSecond >= PICTURE_TIME2) {
                    //第二次拍照
                    takePhoto(sortedAthletes);
                    over = true;
                    break;
                }
            }
        }

        private void sleepThenTakeFirstPhoto() {
            while (true) {
                long currentSecond = (Instant.now().toEpochMilli() - startTime) / 1000 + 1;
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(currentSecond + "秒  ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (PICTURE_TIME <= currentSecond) {
                    System.out.println(currentSecond + "秒");
                    //第一次拍照
                    List<Athlete> sortedAthletes = compute();
                    System.out.print(currentSecond + "秒: ");
                    sortedAthletes.stream().forEach(athlete -> System.out.print(athlete.getName() + " " + athlete.curLength + " "));
                    takePhoto(sortedAthletes);
                    break;
                }
            }
        }

        private <E> void takePhoto(List<E> datas) {
            System.out.print("\ttakePhoto");
            pictures.add(new Picture(datas));
        }

        public void showPhotos() {
            System.out.println("\n打印照片");
            pictures.stream().forEach(e -> System.out.println(e));
        }

    }

    /**
     * 运动健儿
     */
    static class Athlete extends Thread implements Comparable<Athlete>, Cloneable {
        final CountDownLatch latch;
        final String name;
        private volatile int curLength = 0;

        public Athlete(String name, CountDownLatch latch) {
            super(name);
            this.name = name;
            this.latch = latch;
        }

        public void ready() {
            System.out.println(this.name + " 进场");
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("选手" + this.name + " 准备好了,等待发令枪");
            latch.countDown();
        }

        @Override
        public void run() {
            ready();
            try {
                START_GUN.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("选手" + this.name + " 起跑\t");
            doRun();
        }

        private void doRun() {
            while (!over && !isInterrupted()) {
                curLength += ThreadLocalRandom.current().nextInt(8)+1;
                //模拟秒速
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int compareTo(Athlete o) {
            return Integer.compare(this.curLength, o.curLength);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Athlete athlete = new Athlete(this.name, null);
            athlete.curLength = this.curLength;
            return athlete;
        }

        @Override
        public String toString() {
            return "Athlete{" +
                    "name='" + name + '\'' +
                    ", curLength=" + curLength +
                    '}';
        }
    }
}


