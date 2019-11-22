package com.zing.ad.annotation;

import java.lang.annotation.*;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreResponseAdvice {
}
