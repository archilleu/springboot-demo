package com.example.common.base.annotation;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author cjy
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 调用次数
     *
     * @return 次数
     */
    int count() default 60;

    /**
     * 间隔时间，秒为单位
     *
     * @return 间隔时间
     */
    int interval() default 60;
}
