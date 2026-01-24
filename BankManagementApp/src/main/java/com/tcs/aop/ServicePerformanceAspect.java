package com.tcs.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServicePerformanceAspect {

    private static final Logger log = LoggerFactory.getLogger(ServicePerformanceAspect.class);

    @Around("execution(* com.tcs.serviceimpl..*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        log.info("⏱️ {} executed in {} ms",
                joinPoint.getSignature().toShortString(),
                (end - start));

        return result;
    }
}
