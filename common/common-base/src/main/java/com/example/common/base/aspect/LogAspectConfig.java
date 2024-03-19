package com.example.common.base.aspect;

import com.example.common.base.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 日志切面
 *
 * @author cjy
 */
@Slf4j
@Aspect
@Component
public class LogAspectConfig {
    /**
     * 拦截注解类{@link Log}
     */
    final private static String ANNOTATION_NAME = "@annotation(com.example.common.base.annotation.Log)";

    @Around(ANNOTATION_NAME)
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Log annotation = method.getAnnotation(Log.class);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String methodName = joinPoint.getTarget().getClass().getName() + "." + method.getName();
        log.info("\r\n[{}]\r\n方法名: [{}]\r\nIP: [{}]\r\nurl: [{}]", annotation.value(), methodName, request.getRemoteAddr(), request.getRequestURI());

        if (annotation.printParam()) {
            Object[] argValues = joinPoint.getArgs();
            String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < argNames.length; i++) {
                sb.append(argNames[i])
                        .append(": ")
                        .append(argValues[i])
                        .append("\r\n");
            }
            final int end = 2;
            if (sb.length() >= end) {
                sb.delete(sb.length() - end, sb.length());
            }
            log.info("\r\n参数: [{}]", sb);
        }

        Object proceed = joinPoint.proceed();

        if (annotation.printResult()) {
            log.info("\r\n返回数据: {}", proceed);
        }

        log.info("\r\n[{}]执行完成,耗时: [{}]ms", annotation.value(), System.currentTimeMillis() - start);
        return proceed;
    }
}
