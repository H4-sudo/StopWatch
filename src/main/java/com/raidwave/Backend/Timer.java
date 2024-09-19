package com.raidwave.Backend;

public class Timer {
    private int seconds = 0;
    private int milliseconds = 0;
    private boolean running = false;

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void increment() {
        if (running) {
            milliseconds++;
            if (milliseconds >= 1000) {
                seconds++;
                milliseconds = 0;
            }
        }
    }

    public void reset() {
        seconds = 0;
        milliseconds = 0;
    }
}