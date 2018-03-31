package com.jackila.dbdemo.RetentionPolicy.RuntimePolicy;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * create by jackila ON 02/01/2018
 */

public class SimpleObjTest {

    @Test
    public void testGetAnnotation() {
        Annotation[] annotations = SimpleObj.class.getAnnotations();
        System.out.println(Arrays.toString(annotations));
        MyClassRuntimeAnno myClassAnno = SimpleObj.class.getAnnotation(MyClassRuntimeAnno.class);
        System.out.println(myClassAnno.name() + ", " + myClassAnno.level());
        System.out.println(myClassAnno == annotations[0]);
    }
}