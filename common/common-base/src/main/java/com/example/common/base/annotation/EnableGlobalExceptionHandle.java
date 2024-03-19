package com.example.common.base.annotation;


import com.example.common.base.config.GlobalExceptionHandlerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启全局返回值包装注解
 *
 * @author cjy
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(GlobalExceptionHandlerConfiguration.class)
public @interface EnableGlobalExceptionHandle {
}
