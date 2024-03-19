package com.example.common.base.annotation;


import java.lang.annotation.*;

/**
 * 日志输出注解
 *
 * @author cjy
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
    /**
     * 打印日志前缀
     */
    String value() default "方法";

    /**
     * 打印参数
     */
    boolean printParam() default true;

    /**
     * 答应返回结果
     */
    boolean printResult() default true;
}
