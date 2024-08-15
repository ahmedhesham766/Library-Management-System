package com.example.LibraryManagementSystem.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.LibraryManagementSystem.service.*.*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long timeTaken = System.currentTimeMillis() - startTime;

        logger.info("Executed method: {} with arguments: {} in {} ms", joinPoint.getSignature(), joinPoint.getArgs(), timeTaken);

        return result;
    }


    @Before("execution(* com.example.LibraryManagementSystem.service.*.*(..))")
    public void logMethodEntry() {
        logger.info("Entering method");
    }

    @AfterThrowing(pointcut = "execution(* com.example.LibraryManagementSystem.service.*.*(..))", throwing = "ex")
    public void logExceptions(Exception ex) {
        logger.error("An exception has been thrown: {}", ex.getMessage());
    }
}