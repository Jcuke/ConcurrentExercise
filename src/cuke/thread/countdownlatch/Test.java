package cuke.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 3个线程共享一个 总数为3的计数器, 每个人执行完，才将计数器减一
 * countDownLatch.await()方法，即让主线程等待， 线程调度器会盯住计数器的状态，直到为0， await() 下面的代码才开始执行(即主线程的代码开始执行)
 *
 * 其功臣有个外号叫发令枪，即所有拥有这个计数器的线程执行完，主线程才开始执行
 *
 * 1、2、3线程是并行执行，使用场景，前期并发处理一些业务，准备资源，都准备好之后才处理其他业务
 *
 * 计数器 为0之后，无法重置，只能使用一次，这也是和 CyclicBarrier 的最大的区别
 */
public class Test {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(new T("1", countDownLatch)).start();
        new Thread(new T("2", countDownLatch)).start();
        new Thread(new T("3", countDownLatch)).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("主线程开始阻塞");
        //Thread.sleep(5000);
        System.out.println("主线程执行完毕");

    }

    public static class T implements Runnable {

        String threadName;
        CountDownLatch countDownLatch;

        public T(String threadName, CountDownLatch countDownLatch) {
            this.threadName = threadName;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println("Thread name:" + this.threadName + " run start");
                Thread.sleep(2000l);
                System.out.println("Thread name:" + this.threadName + " run end");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
