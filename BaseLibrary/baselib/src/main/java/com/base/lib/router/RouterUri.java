package com.base.lib.router;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by allen on 2016/12/7.
 */

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface RouterUri {
    String routerUri() default "";
}
