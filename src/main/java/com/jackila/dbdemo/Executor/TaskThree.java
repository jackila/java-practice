package com.jackila.dbdemo.Executor;

/**
 * create by jackila ON 22/01/2018
 */
public class TaskThree implements Runnable {
    @Override
    public void run() {
        System.out.println("Executing Task Three");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
