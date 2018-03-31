package com.jackila.dbdemo.JavaConcurrentArtist.FinalManage;

import org.junit.Test;

/**
 * create by jackila ON 27/01/2018
 */

public class FinalReferenceEscapeExampleTest{

    @Test
    public void testWriter() throws Exception {

        while(true){
            FinalReferenceEscapeExample.reader();
            FinalReferenceEscapeExample.writer();
        }

    }

    public void testReader() throws Exception {

    }



}