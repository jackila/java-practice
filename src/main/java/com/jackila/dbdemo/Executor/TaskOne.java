package com.jackila.dbdemo.Executor;

/**
 * create by jackila ON 22/01/2018
 */
public class TaskOne implements Runnable {

    @Override
    public void run() {

        System.out.println("Executing Task One");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
