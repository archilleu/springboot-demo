package com.haoya.demo.common.AOP;

import com.alibaba.fastjson.JSONObject;
import com.haoya.demo.common.annotation.SysLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 注解系统日志切面
 */
@Aspect
@Configuration
public class SysLoggerAOP {

    @SuppressWarnings("unused")
    @Pointcut("@annotation(com.haoya.demo.common.annotation.SysLogger)")
    public void loggerPointCut(){}

    @SuppressWarnings("unused")
    @Around("loggerPointCut()")
    public Object sysLogger(ProceedingJoinPoint point) throws Throwable {
        long begin = System.currentTimeMillis();
        Object result = point.proceed();
        long total = System.currentTimeMillis() - begin;

        //保存日志
        save(point, total);

        return result;
    }

    private void save(ProceedingJoinPoint point, long time) {
        String className = point.getTarget().getClass().getName();

        MethodSignature signature = (MethodSignature)point.getSignature();
        String methodName = signature.getName();

        Object params = point.getArgs();
        if(null != params) {
            JSONObject.toJSONString(params);
        }

        Method method = signature.getMethod();
        SysLogger logger = method.getAnnotation(SysLogger.class);
        String value = logger.value();

        //TODO: ip

        //TODO: username

        return;
    }

}
