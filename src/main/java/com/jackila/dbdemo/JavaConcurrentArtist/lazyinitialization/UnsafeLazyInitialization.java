package com.jackila.dbdemo.JavaConcurrentArtist.lazyinitialization;

/**
 * create by jackila ON 27/01/2018
 */
public class UnsafeLazyInitialization {

    private static Instance instance;

    public static Instance getInstance(){

        if(instance == null){
            instance = new Instance();
        }
        return instance;

    }
}
