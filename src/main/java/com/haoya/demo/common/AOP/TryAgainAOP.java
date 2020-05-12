package com.haoya.demo.common.AOP;

import com.haoya.demo.app.exception.AppExceptionServerError;
import com.haoya.demo.common.annotation.TryAgain;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 并发修改同一记录时，避免更新丢失，需要加锁。要么在应用层加锁，要么在缓存加锁，要么在数据库层使用乐观锁，使用version作为更新依据。
 * 说明：如果每次访问冲突概率小于20%，推荐使用乐观锁，否则使用悲观锁。乐观锁的重试次数不得小于3次。
 */

@Aspect
@Component
public class TryAgainAOP {

    @Pointcut("@annotation(com.haoya.demo.common.annotation.TryAgain)")
    public void tryAgain() {
    }

    @Around("tryAgain()")
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        //获取被调用的对象以及TryAgain注解对象
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Object calledObject = pjp.getTarget();
        TryAgain tryAgain = calledObject.getClass().getDeclaredMethod(methodName, parameterTypes).getAnnotation(TryAgain.class);

        try {
            while (aspectShouldTryAgain(calledObject, tryAgain)) {
                try {
                    Object result = pjp.proceed(pjp.getArgs());
                    return result;
                } catch (AppExceptionServerError e) {
                    //重试
                } catch (ObjectOptimisticLockingFailureException e) {
                    //重试
                } catch (Exception e) {
                    //其他情况
                    String handleExceptionMethodName = tryAgain.handleException();
                    if (!StringUtils.isEmpty(handleExceptionMethodName)) {
                        try {
                            Method handleException = calledObject.getClass().getDeclaredMethod(handleExceptionMethodName);
                            handleException.setAccessible(true);
                            handleException.invoke(calledObject, new Object[]{});
                        } catch (Exception ex) {
                            throw ex;
                        }
                    }

                    aspectBeforeExceptionReturn(calledObject, tryAgain);
                    throw e;
                }
            }

        } finally {
            retryCounters.set(0);
        }

        return null;
    }

    //失败返回前的处理
    private void aspectBeforeExceptionReturn(Object calledObject, TryAgain tryAgain) throws Throwable {
        String beforeExceptionalReturnMethodName = tryAgain.beforeExceptionalReturn();
        if(!StringUtils.isEmpty(beforeExceptionalReturnMethodName)) {
            Method beforeExceptionalReturn = calledObject.getClass().getDeclaredMethod(beforeExceptionalReturnMethodName);
            beforeExceptionalReturn.setAccessible(true);
            beforeExceptionalReturn.invoke(calledObject, new Object[]{});
        }

        return;
    }

    //是否进行重试
    private boolean aspectShouldTryAgain(Object calledObject, TryAgain tryAgain) throws Throwable {
        Integer currentCount = retryCounters.get();
        if(++currentCount > tryAgain.tryTimes()) {
            return false;
        }
        retryCounters.set(currentCount);

        boolean shouldRetry = false;
        String shouldRetryMethodName = tryAgain.shouldRetry();
        if(StringUtils.isEmpty(shouldRetryMethodName)) {
            shouldRetry = true;
        } else {
            try {
                Method shouldRetryMethod = calledObject.getClass().getDeclaredMethod(shouldRetryMethodName);
                shouldRetryMethod.setAccessible(true);
                shouldRetry = (boolean) shouldRetryMethod.invoke(calledObject, new Object[]{});
            } catch (Exception ex) {
                shouldRetry = true;
            }
        }

        return shouldRetry;
    }

    static {
        retryCounters = ThreadLocal.withInitial(()->{return 0;});
    }
    private static ThreadLocal<Integer> retryCounters;
}
