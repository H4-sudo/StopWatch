package com.raidwave.Backend;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements Counter  {
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void increment() {
        counter.incrementAndGet();
    }

    public int getValue() {
        return counter.get();
    }
}
