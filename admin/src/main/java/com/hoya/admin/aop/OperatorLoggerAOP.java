package com.hoya.admin.aop;

import com.alibaba.fastjson.JSONObject;
import com.hoya.admin.annotation.OperatorLogger;
import com.hoya.admin.model.sys.SysLog;
import com.hoya.admin.server.sys.SysLogService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.utils.IPAddress;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Configuration
public class OperatorLoggerAOP {

    @Autowired
    SysLogService sysLogService;

    @Pointcut("@annotation(com.hoya.admin.annotation.OperatorLogger)")
    public void loggerPointCut() {
    }

    @Around("loggerPointCut()")
    public Object logger(ProceedingJoinPoint point) throws Throwable {
        long begin = System.currentTimeMillis();
        Object result = point.proceed();
        long total = System.currentTimeMillis() - begin;

        //保存日志
        save(point, total);

        return result;
    }

    private void save(ProceedingJoinPoint point, long time) {
        SysLog sysLog = new SysLog();

        sysLog.setTime(time);
        sysLog.setCreateTime(LocalDateTime.now());

        MethodSignature signature = (MethodSignature) point.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        sysLog.setMethod(methodName);

        Object params = point.getArgs();
        if (null != params) {
            sysLog.setParams(JSONObject.toJSONString(params));
        }

        Method method = signature.getMethod();
        OperatorLogger logger = method.getAnnotation(OperatorLogger.class);
        sysLog.setOperation(logger.value());

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        sysLog.setIp(IPAddress.getIPAddress(request));

        //获取用户名
        sysLog.setUserName(SecurityUtils.getUsername());

        //保存到数据库
        sysLogService.save(sysLog);

        //输出日志文件
        log.info(JSONObject.toJSONString(sysLog));
        return;
    }
}
