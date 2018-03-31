package com.jackila.dbdemo.JavaConcurrentArtist.lazyinitialization;

/**
 * create by jackila ON 27/01/2018
 *
 * 由于执行过程中的字节码的重构，导致初始化对象的顺序发生变化，可能导致严重的
 * 空指针问题
 */
public class DoubleCheckLocking {
    private static Instance instance;

    public static Instance getInstance() {

        if(instance == null){
            synchronized (DoubleCheckLocking.class){
                if(instance == null){
                    instance = new Instance();
                }
            }
        }
        return instance;
    }
}
