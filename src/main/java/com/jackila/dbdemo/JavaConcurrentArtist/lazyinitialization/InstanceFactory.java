package com.jackila.dbdemo.JavaConcurrentArtist.lazyinitialization;

/**
 * create by jackila ON 27/01/2018
 */
public class InstanceFactory {
    private static class InstanceHolder{
        public static Instance instance = new Instance();
    }

    public static Instance getInstance(){
        return InstanceHolder.instance;
    }
}
