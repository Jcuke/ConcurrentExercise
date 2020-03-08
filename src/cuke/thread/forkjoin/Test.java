package cuke.thread.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ForkJoinPool pool = new ForkJoinPool();

        SumTask sumTask = new SumTask(0, 100l);

        ForkJoinTask<Long> submit = pool.submit(sumTask);

        //get 方法会阻塞
        long sum = submit.get();

        System.out.println("sum:" + sum);
    }
}
