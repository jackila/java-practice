package com.jackila.dbdemo.JavaConcurrentArtist;

/**
 * create by jackila ON 27/01/2018
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;
    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready)   Thread.yield();
            System.out.println(number);
            //maybe it will be 0
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();

        number = 42;
        ready = true;
        System.out.println("end");
    }
}