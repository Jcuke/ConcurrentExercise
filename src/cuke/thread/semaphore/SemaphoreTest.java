package cuke.thread.semaphore;

import lombok.Data;

import java.util.concurrent.Semaphore;

/**
 * 可见，总共5个线程，同时进入 acquire() 最多只有俩线程
 * 这样 Semaphor 即可控制并发数
 *
 * 每俩个线程执行完（执行完的标志即 end 打印出来）接下来的俩个线程才执行run（即打印 run start）
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for (Integer i = 0; i < 5; i++) {
            final T t = new T(String.valueOf(i), semaphore);
            new Thread(t).start();
        }
    }

    @Data
    public static class T implements Runnable {

        String threadName;
        Semaphore semaphore;

        public T(String threadName, Semaphore semaphore) {
            this.threadName = threadName;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("Thread acquire name:" + this.threadName + " run start");
                Thread.sleep(2000l);
                System.out.println("Thread acquire name:" + this.threadName + " run end");
                semaphore.release();
                System.out.println("》》》》》》》》》》》》》Thread release name:" + this.threadName + " run end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
