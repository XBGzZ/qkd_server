package com.xbg.qkd_server.config.catcher;

import com.xbg.qkd_server.common.errors.KMEException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author XBG
 * @Description: 配置项异常，直接退出，减少错误日志打印，方便定位
 * @Date 2025-01-31
 */
@Slf4j
@Aspect
@Component
public class ConfigErrorCatcher {

    @Around("@annotation(org.springframework.context.annotation.Bean)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (KMEException e) {
            log.error("catch business error", e);
            System.exit(0);
            return null;
        }
    }
}
