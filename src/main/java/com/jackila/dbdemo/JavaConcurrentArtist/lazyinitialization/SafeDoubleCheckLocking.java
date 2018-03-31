package com.jackila.dbdemo.JavaConcurrentArtist.lazyinitialization;

/**
 * create by jackila ON 27/01/2018
 *禁止对象实例化过程中的重排序
 *
 */
public class SafeDoubleCheckLocking {
    private volatile static Instance instance;

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
