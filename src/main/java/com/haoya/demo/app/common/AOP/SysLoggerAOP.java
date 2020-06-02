package com.haoya.demo.app.common.AOP;

import com.haoya.demo.app.common.annotation.SysLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 注解系统日志切面
 */
@Aspect
@Configuration
public class SysLoggerAOP {

    Logger logger = LoggerFactory.getLogger(SysLogger.class);

    @SuppressWarnings("unused")
    @Pointcut("@annotation(com.haoya.demo.app.common.annotation.SysLogger)")
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

        String paramsStr = null;
        StringBuilder sb = new StringBuilder();
        Object[] params = point.getArgs();
        if(null != params) {
            for(Object param : params) {
                sb.append(param.toString()).append(",");
            }

            paramsStr = sb.toString();
        }

        Method method = signature.getMethod();
        SysLogger sysLogger = method.getAnnotation(SysLogger.class);

        //TODO: ip

        //TODO: username

        sb = new StringBuilder();
        sb.append("[desc: " + sysLogger.value()).append("]");
        sb.append("[class name: " + className).append("]");
        sb.append("[method name: " + methodName).append("]");
        sb.append("[params: " + paramsStr).append("]");

        logger.info(sb.toString());
        return;
    }

}
