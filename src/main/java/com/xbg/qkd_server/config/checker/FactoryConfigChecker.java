package com.xbg.qkd_server.config.checker;

import com.xbg.qkd_server.infrastructure.keyManager.factory.strategy.FactoryStrategy;
import org.aspectj.lang.JoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-28 21:51
 */
@Component
public class FactoryConfigChecker extends BaseConfigChecker<ConditionalOnProperty> {
    @Override
    protected Boolean checkAnnotationValid(JoinPoint joinPoint, String methodName, ConditionalOnProperty annotation) {
        return FactoryStrategy.contain(annotation.havingValue());
    }

    @Override
    protected Class<ConditionalOnProperty> getCheckPoint() {
        return ConditionalOnProperty.class;
    }
}
