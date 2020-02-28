package cuke.thread.join;

/**
 * t.join()執行的意义
 * t 的run()方法体执行完毕之后，t.join()下面的代码才开始执行，否则，一致阻塞
 */
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new JoinTest.T("t1"));
        Thread t2 = new Thread(new JoinTest.T("t2"));

        t1.start();
        t1.join();
        System.out.println("t1.join end");

        t2.start();
        t2.join();
        System.out.println("t2.join end");
        System.out.println("t1.join end, t2.join end");
    }

    public static class T implements Runnable {

        String threadName;

        public T(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            try {
                System.out.println("Thread name:" + this.threadName + " run start");
                Thread.sleep(2000l);
                System.out.println("Thread name:" + this.threadName + " run end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


