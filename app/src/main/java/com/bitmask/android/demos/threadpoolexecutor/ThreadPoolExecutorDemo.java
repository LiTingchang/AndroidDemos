package com.bitmask.android.demos.threadpoolexecutor;

import android.util.Log;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 *
 *
 *
 */

public class ThreadPoolExecutorDemo {

    private static final String TAG = ThreadPoolExecutorDemo.class.getSimpleName();

    public static void runThreadPoolExecutor() {


        int numberOfCores = Runtime.getRuntime().availableProcessors();
        Log.d(TAG, "Number of cores : " + numberOfCores);

        // 指定优先级队列的初始容量，默认为 Integer.MAX_VALUE
        BlockingDeque<Runnable> fifoQueue = new LinkedBlockingDeque<>(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                numberOfCores, // 核心线程数
                numberOfCores * 2, //最大线程数
                1, TimeUnit.SECONDS, // 非核心线程存活时间
                fifoQueue,
                new BackgroundThreadFactory()
        );

        for (int i = 0; i < 10; ++i){
            final int taskNumber = i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    Log.d(TAG, "Task: " + taskNumber + ", thread: " + name + " started");
                    waitHere(200); // work here
                    Log.d(TAG, "Task: " + taskNumber + ", thread: " + name + " done");
                }
            });
        }

        // 关闭线程池
        threadPoolExecutor.shutdown();
        try {
            if (!threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutor.shutdownNow();
        }
    }

    public static void runThreadPoolExecutor2() {
        MyExecutor myExecutor = MyExecutor.getMyExecuter();


        for (int i = 0; i < 10; ++i){
            final int taskNumber = i;
            myExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    Log.d(TAG, "Task: " + taskNumber + ", thread: " + name + " started");
                    waitHere(200); // work here

                    if (taskNumber == 3) {
                        throw new RuntimeException("This is an uncaught exception!");
                    }

                    Log.d(TAG, "Task: " + taskNumber + ", thread: " + name + " done");
                }
            });
        }

        // 关闭线程池
        myExecutor.shutdown();
        try {
            if (!myExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                myExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            myExecutor.shutdownNow();
        }
    }

    public static final void waitHere(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class BackgroundThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("CustomThread-ExecutorDemo");
            thread.setPriority(Thread.NORM_PRIORITY);
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    Log.e("THREAD_ERROR", thread.getName() + " encountered an error: " + ex.getMessage());
                }
            });
            return thread;
        }
    }

    static class MyExecutor extends ThreadPoolExecutor {
        private static final MyExecutor myExecuter = new MyExecutor();
        public static MyExecutor getMyExecuter(){
            return myExecuter;
        }

        private MyExecutor () {
            super(
                    Runtime.getRuntime().availableProcessors(),
                    Runtime.getRuntime().availableProcessors() * 2,
                    1, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(),
                    new BackgroundThreadFactory()
            );
        }
    }
}
