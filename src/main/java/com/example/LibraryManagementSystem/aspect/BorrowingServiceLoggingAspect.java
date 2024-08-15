package com.example.LibraryManagementSystem.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BorrowingServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(BorrowingServiceLoggingAspect.class);

    @Around("execution(* com.example.LibraryManagementSystem.service.Impl.BorrowingServiceImpl.*(..))")
    public Object logBorrowingServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            logger.error("Exception in method: {} with message: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex;
        }

        long timeTaken = System.currentTimeMillis() - startTime;

        // Log method exit and execution time
        logger.info("Exiting method: {} executed in {} ms", joinPoint.getSignature(), timeTaken);

        return result;
    }
}