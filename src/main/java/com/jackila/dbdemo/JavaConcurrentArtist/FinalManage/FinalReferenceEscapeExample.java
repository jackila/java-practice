package com.jackila.dbdemo.JavaConcurrentArtist.FinalManage;

/**
 * create by jackila ON 27/01/2018
 *
 * 在final的内存语义分析中有一种需要避免的
 * 编程方式，这种方式会导致final的值无法保证规定
 * 的重排序规则
 *
 */
public class FinalReferenceEscapeExample {

    final int i ;
    static FinalReferenceEscapeExample obj;
    public FinalReferenceEscapeExample (){
        //1。写final域
        i = 1;
        //2。this引用在此"逸出"
        obj = this;
    }

    public static void writer(){
        new FinalReferenceEscapeExample();
    }

    public static void reader(){
        if (obj != null){
            int temp = obj.i;
        }
    }

}
