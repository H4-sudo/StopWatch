package com.raidwave.Backend;

public class SynchronizedCounter implements Counter {
    private int counter = 0;

    @Override
    public synchronized void increment() {
        counter++;
    }

    @Override
    public synchronized int getValue() {
        return counter;
    }
}
