package com.example.librarymanagement.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.librarymanagement.services.BookService.*(..)) "
            + "|| execution(* com.example.librarymanagement.services.PatronService.*(..)) "
            + "|| execution(* com.example.librarymanagement.services.BorrowingService.*(..))")
    public void loggingPointcut() {
    }

    @Before("loggingPointcut()")
    public void logMethodCall(JoinPoint joinPoint) {
        logger.info("Method {} called with arguments {}", joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "loggingPointcut()", returning = "result")
    public void logReturnValue(JoinPoint joinPoint, Object result) {
        logger.info("Method {} returned with value {}", joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(pointcut = "loggingPointcut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method {}: {}", joinPoint.getSignature().toShortString(), exception.getMessage());
    }

}
