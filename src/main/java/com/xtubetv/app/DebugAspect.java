package com.xtubetv.app;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DebugAspect {
    @Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..))")// the pointcut expression
    private void repository() { }

    @Before("repository()")// the pointcut expression
    private void anyOldTransfer(JoinPoint joinPoint) {
        final String name = joinPoint.getSignature().getName();
        final String target = joinPoint.getTarget().toString();
        System.out.printf("=======================target : %s, method: %s\n", target, name);
    }
}
