package com.raidwave.Threading;

import com.raidwave.Backend.Counter;

public class CounterThread implements Runnable{
    private final Counter counter;
    private final int increments;

    public CounterThread(Counter counter, int increments) {
        this.counter = counter;
        this.increments = increments;
    }

    @Override
    public void run() {
        for (int i = 0; i < increments; i++) {
            counter.increment();
        }
    }
}
