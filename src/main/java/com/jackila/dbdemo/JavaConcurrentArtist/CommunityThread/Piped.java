package com.jackila.dbdemo.JavaConcurrentArtist.CommunityThread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * create by jackila ON 29/01/2018
 */
public class Piped {
    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);

        Thread printThread = new Thread(new Print(in),"PrintThread");
        printThread.start();
        int received =0;

        try{
            while((received = System.in.read()) != -1){
                out.write(received);
            }
        } finally {
            out.close();
        }
    }

    static class Print implements Runnable{

        PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int received = 0;
            try {
                while((received = in.read()) != -1){
                    System.out.print((char)received);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
