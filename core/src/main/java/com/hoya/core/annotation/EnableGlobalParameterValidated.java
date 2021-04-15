package com.hoya.core.annotation;


import com.hoya.core.aop.ParameterValidatedAop;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.hoya.core.aop.annotation.ParameterValidated;

/**
 * 开启全局controller参数校验{@link ParameterValidated}
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ParameterValidatedAop.class)//显示导入类，不然spring boot 默认扫描不到这个类
public @interface EnableGlobalParameterValidated {
}
