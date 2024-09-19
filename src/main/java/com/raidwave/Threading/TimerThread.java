package com.raidwave.Threading;

import com.raidwave.Backend.Timer;

public class TimerThread implements Runnable {
    private final Timer timer;

    public TimerThread(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        while (timer.isRunning()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
            timer.increment();
        }
    }
}
