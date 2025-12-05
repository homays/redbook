package com.ymkx.redbook.auth.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

/**
 * @author ymkx
 */
@Aspect
@Component
public class RetryLogAspect {

    @Pointcut("@annotation(org.springframework.retry.annotation.Retryable)")
    public void retryableMethod() {
    }

    @Around("retryableMethod()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 自动放入 RetryContext，让后续重试日志能用
        RetryContext context = RetrySynchronizationManager.getContext();
        if (context != null) {
            context.setAttribute("context", pjp);
        }

        return pjp.proceed();
    }
}
