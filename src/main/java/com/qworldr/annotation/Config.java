package com.qworldr.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Config {

    public String value() default  "";
    public  boolean reload() default false;
    public String suffix() default  "";
    public String path() default  "";
}
