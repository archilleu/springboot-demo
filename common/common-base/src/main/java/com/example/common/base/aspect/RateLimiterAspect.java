package com.example.common.base.aspect;

import com.example.common.base.annotation.RateLimiter;
import com.example.common.base.exception.ServerExceptionForbidden;
import com.example.common.base.utils.IpAddressHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * 限流注解切面{@link com.example.common.base.annotation.RateLimiter}
 *
 * @author cjy
 */
@Aspect
@Component
@Slf4j
public class RateLimiterAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint joinPoint, RateLimiter rateLimiter) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RateLimiter annotation = method.getAnnotation(RateLimiter.class);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = IpAddressHelper.getIPAddress(request);
        String methodName = method.getName();
        String key = String.format("interfaceRateLimit:%s_%s", ip, methodName);
        // TODO: 可以做成配置项
        int count = annotation.count();
        int interval = annotation.interval();

        Long value = redisTemplate.opsForValue().increment(key, 1);
        log.debug("server[{}]visit interface[{}]count[{}]times", ip, methodName, value);
        if (1 == value) {
            redisTemplate.expire(key, interval, TimeUnit.SECONDS);
        }

        if (value > count) {
            Long expire = redisTemplate.getExpire(key);
            String errMsg = String.format("server[%s]visit interface[%s]count[%d]times, limit is [%d/%ds], refresh time remain[%d]s",
                    ip, methodName, value, count, interval, expire);
            log.debug(errMsg);
            throw new ServerExceptionForbidden(errMsg);
        }
    }

}
