package cuke.thread.forkjoin;

import java.util.concurrent.RecursiveTask;


/**
 * 求1到100百万之间所有证书的和
 * <p>
 * 递归把一个任务与拆分成两份，每个子任务在拆分成两份,以此递归
 * <p>
 * demo B站视频地址 https://www.bilibili.com/video/av90007319?p=28
 */
public class SumTask extends RecursiveTask<Long> {

    long start;
    long end;
    long step = 10;//步长

    public SumTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;

        if (end - start > step) {

            for (long i = start; i <= end; i++) {
                sum += i;
            }

        } else {

            long middle = (start + end) / 2;
            SumTask task1 = new SumTask(start, middle);
            task1.fork();

            SumTask task2 = new SumTask(middle + 1, end);
            task2.fork();

            return task1.join() + task2.join();

        }

        return sum;
    }


}
