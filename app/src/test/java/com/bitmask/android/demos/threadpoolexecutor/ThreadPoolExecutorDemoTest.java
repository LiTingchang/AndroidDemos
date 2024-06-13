package com.bitmask.android.demos.threadpoolexecutor;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorDemoTest {

    @Test
    public void runThreadPoolExecutor() {
        ThreadPoolExecutorDemo.runThreadPoolExecutor();
        ThreadPoolExecutorDemo.runThreadPoolExecutor2();
    }
}