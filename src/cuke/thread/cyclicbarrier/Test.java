package cuke.thread.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 所有的子线程执行完成.new CyclicBarrier() 第二个参数的线程才开始执行
 *
 * CyclicBarrier 功能与 CyclicBarrier 类似
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Thread(()->{
            System.out.println("此线程叫cyclicBarrier all task finished");
        }));

        new Thread(new T("1",cyclicBarrier)).start();
        new Thread(new T("2",cyclicBarrier)).start();
        new Thread(new T("3",cyclicBarrier)).start();
        new Thread(new T("4",cyclicBarrier)).start();
        new Thread(new T("5",cyclicBarrier)).start();

        System.out.println("main thread start");
        Thread.sleep(1000l);
        System.out.println("main thread end");
    }

    public static class T implements Runnable {

        String threadName;
        CyclicBarrier cyclicBarrier;

        public T(String threadName, CyclicBarrier cyclicBarrier) {
            this.threadName = threadName;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("Thread name:" + this.threadName + " run start");
                Thread.sleep(5000l);
                System.out.println("Thread name:" + this.threadName + " start await 子线程准备就绪");
                cyclicBarrier.await();
                System.out.println("Thread name:" + this.threadName + " run end");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
