package com.example.oblig2dat153.Utils;

public class Timer implements Runnable {
    private long timeInMillis;

    public Timer(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

