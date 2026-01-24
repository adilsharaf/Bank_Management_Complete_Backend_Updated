package com.tcs.aop;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(ExceptionLoggingAspect.class);

    @AfterThrowing(
        pointcut = "execution(* com.tcs.serviceimpl..*(..))",
        throwing = "ex"
    )
    public void logExceptions(Exception ex) {
        log.error("❌ Exception occurred: {}", ex.getMessage(), ex);
    }
}
