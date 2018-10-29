package com.hoya.app.demo.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AOPConfig {
	@Around("@within(org.springframework.stereotype.Controller)")
	public Object simpleAop(final ProceedingJoinPoint pjp) throws Throwable {
		try {
			Object[] args = pjp.getArgs();
			System.out.println("args:" + Arrays.asList(args));
			Object o = pjp.proceed();
			System.out.println("return :" + o);
			return o;
		} catch(Throwable e) {
			throw e;
		}
	}
}