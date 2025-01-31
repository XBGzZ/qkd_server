package com.xbg.qkd_server.config.checker;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *     配置文件检测小助手基类
 *     提供对Config类的校验，帮助检测配置文件是否书写规范
 * </pre>
 *
 * @author XBG
 * @date 2025-01-28 21:30
 */
@Slf4j
@Aspect
@Component
public abstract class BaseConfigChecker<T extends Annotation> implements ConfigChecker {

    @Before("@annotation(com.xbg.qkd_server.common.annotations.ConfigCheck)")
    public void around(JoinPoint joinPoint) throws Throwable {
        var anno = getTargetAnnotation(joinPoint);
        if (anno.isEmpty()) {
            return ;
        }
        String methodName = joinPoint.getSignature().getName();
        if(!checkAnnotationValid(joinPoint,methodName,anno.get())) {
            log.warn("method [{}] annotation [{}] is not standard format", methodName,anno.getClass().getSimpleName());
        }
    }

    protected Optional<T> getTargetAnnotation(JoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 使用工具类解析原始类，避免CGLIB代理后，丢失注解
        Class<?> targetClass = ClassUtils.getUserClass(joinPoint.getTarget());
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        return Optional.ofNullable(objMethod.getDeclaredAnnotation(getCheckPoint()));
    }

    protected abstract Boolean checkAnnotationValid(JoinPoint joinPoint,String methodName,T annotation);

    protected abstract Class<T> getCheckPoint();

}
