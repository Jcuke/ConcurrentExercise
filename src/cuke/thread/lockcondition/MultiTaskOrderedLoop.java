package cuke.thread.lockcondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程，按顺序交替执行
 */
public class MultiTaskOrderedLoop {

    public static void main(String[] args) {
        Loop loop = new Loop();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                loop.taskA();
            }
        }).start();


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                loop.taskB();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                loop.taskC();
            }
        }).start();
    }


}

class Loop {

    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();
    int number = 1;

    public void taskA() {
        lock.lock();
        try {
            System.out.println("taskA run start");
            while (number != 1) {
                condition1.await();
            }
            System.out.println("taskA run end");
            number = 2;
            condition2.signal();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void taskB() {
        lock.lock();
        try {
            System.out.println("taskB run start");
            while (number != 2) {
                condition2.await();
            }
            System.out.println("taskB run end");
            number = 3;
            condition3.signal();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void taskC() {
        lock.lock();
        try {
            System.out.println("taskC run start");
            while (number != 3) {
                condition3.await();
            }
            System.out.println("taskC run end");
            number = 1;
            condition1.signal();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}


