package cuke.thread.lockcondition;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Condition.html#targetText=The%20key%20property%20that%20waiting,use%20its%20newCondition()%20method.
 * 来自官网的实例
 * <p>
 * 生产者与消费者
 */
class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[10];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BoundedBuffer bb = new BoundedBuffer();
        new Thread(() -> {
            while (true) {
                try {
                    int ele = new Random().nextInt(100);
                    bb.put(ele);
                    List<String> eles = Arrays.asList(bb.items).stream().filter(x -> x != null).map(x -> x.toString()).collect(Collectors.toList());
                    System.out.println("存入一个元素 ele: " + ele + ",items size: " + eles.size() + "items : " + String.join(",", eles));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    int ele = (Integer) bb.take();
                    List<String> eles = Arrays.asList(bb.items).stream().filter(x -> x != null).map(x -> x.toString()).collect(Collectors.toList());
                    System.out.println("take一个元素 ele:" + ele + ",items size: " + eles.size() + ",items : " + String.join(",", eles));
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}