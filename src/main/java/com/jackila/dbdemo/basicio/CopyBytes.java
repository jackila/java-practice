package com.jackila.dbdemo.basicio;

import java.io.*;
import java.net.URL;

/**
 * create by jackila ON 29/03/2018
 */
public class CopyBytes {


    /**
     *
     * 最简单的IO读取文件
     *
     *
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {


        FileInputStream in = null;
        FileOutputStream out = null;

        System.out.println((byte)'I');

        try {
            String resource = CopyBytes.class.getClassLoader().getResource("xanadu.txt").getPath();
            //System.out.println(new File("./").getPath());
            System.out.println(resource);
            in = new FileInputStream(resource);
            out = new FileOutputStream("outagain.txt");
            int c;

            while ((c = in.read()) != -1) {
                System.out.println(c);
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }


    }

}
