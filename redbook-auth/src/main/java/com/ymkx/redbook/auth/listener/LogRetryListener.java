package com.ymkx.redbook.auth.listener;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author ymkx
 */
@Slf4j
@Component
public class LogRetryListener implements RetryListener {

    @Override
    public <T, E extends Throwable> boolean open(RetryContext retryContext, RetryCallback<T, E> retryCallback) {
        return true;
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        int retry = context.getRetryCount();
        ProceedingJoinPoint pjp = (ProceedingJoinPoint) context.getAttribute("context");
        Object[] args = pjp.getArgs();
        String request = null;
        if (args != null && args.length > 0) {
            request = JSON.toJSONString(Arrays.toString(args));
        }

        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        log.warn("【第 {} 次重试失败】method={}, request={}，error={}", retry, method, request, throwable.getMessage());
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        if (throwable != null) {
            ProceedingJoinPoint pjp = (ProceedingJoinPoint) context.getAttribute("context");
            Object[] args = pjp.getArgs();
            String request = null;
            if (args != null && args.length > 0) {
                request = JSON.toJSONString(Arrays.toString(args));
            }

            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            log.error("【最终失败】method={}, request={}，error={}", method, request, throwable.getMessage());
        }
    }
}
