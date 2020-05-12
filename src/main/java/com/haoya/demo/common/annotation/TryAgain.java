package com.haoya.demo.common.annotation;

/**
 * 乐观锁重试
 */

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TryAgain {
    /**
     * 重试次数
     * @return
     */
    int tryTimes() default 3;

    /**
     * 判断是否需要重试的扩展点，传入的是一个方法名
     * @return
     */
    String shouldRetry() default "";

    /**
     * 在发生异常后进行处理的扩展点，传入的是一个方法名
     * @return
     */
    String handleException() default "";

    /**
     * 在所有重试都失败后进行处理的扩展点，传入的是一个方法名
     * @return
     */
    String beforeExceptionalReturn() default "";
}
