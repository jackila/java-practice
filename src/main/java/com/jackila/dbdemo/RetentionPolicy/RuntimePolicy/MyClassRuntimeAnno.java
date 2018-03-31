package com.jackila.dbdemo.RetentionPolicy.RuntimePolicy;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create by jackila ON 02/01/2018
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyClassRuntimeAnno {

    String name();
    int level() default 1;

}
