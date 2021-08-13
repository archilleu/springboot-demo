package com.starter.common.aop;

import com.starter.common.utils.RequestParametersCheck;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

@Aspect
@Configuration
public class ParameterValidatedAop {

    @Pointcut("@annotation(com.starter.common.aop.annotation.ParameterValidated)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object logger(ProceedingJoinPoint point) throws Throwable {
        List<Object> list = Arrays.asList(point.getArgs());
        BindingResult bindingResult = (BindingResult) list.stream().filter(param ->
                param instanceof BindingResult).findFirst().orElse(null);

        RequestParametersCheck.check(bindingResult);

        return point.proceed();
    }

}
