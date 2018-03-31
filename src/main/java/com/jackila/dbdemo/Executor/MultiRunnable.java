package com.jackila.dbdemo.Executor;

import java.util.List;

/**
 * create by jackila ON 22/01/2018
 */
public class MultiRunnable implements Runnable {
    private final List<Runnable> runnables;

    public MultiRunnable(List<Runnable> runnables) {
        this.runnables = runnables;
    }

    @Override
    public void run() {
        for (Runnable runnable : runnables) {
            new Thread(runnable).start();
        }
    }
}
