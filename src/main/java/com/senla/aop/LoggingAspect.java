package com.senla.aop;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Log4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("@annotation(com.senla.annotation.Logging)")
    public void logging() {
    }

    @AfterReturning(value = "logging()", returning = "returningValue")
    public void loggingAfter(JoinPoint joinPoint, Object returningValue) {
        if (returningValue != null) {
            log.debug("Method: "
                    + joinPoint.getSignature().getName() +
                    ", выходящий: "
                    + returningValue);
        }
    }

    @Before("logging()")
    public void loggingBefore(JoinPoint joinPoint) {
        if (joinPoint.getArgs() != null) {
            log.debug("Method: "
                    + joinPoint.getSignature().getName() +
                    ", входящий: "
                    + Arrays.toString(joinPoint.getArgs()));
        }
    }

    @Pointcut("@annotation(com.senla.annotation.LoggingException)")
    public void loggingException() {
    }

    @AfterReturning(value = "loggingException()", returning = "returningValue")
    public void loggingExceptionAfter(Object returningValue) {
        log.error(returningValue);
    }
}
