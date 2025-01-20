package com.xbg.qkd_server.infrastructure.keyManager.config;

import com.xbg.qkd_server.infrastructure.keyManager.factory.FactoryStrategy;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author XBG
 * @description: TODO
 * @date 2025/1/21 0:36
 */
@Data
@Component
@ConfigurationProperties(prefix = "key-manager.key-factory-config")
public class KeyFactoryConfig  {
    // 工厂策略
    private FactoryStrategy strategy;
    // 最小密钥长度
    private Integer minKeySize = 128;
    // 密钥默认长度
    private Integer keySize = 256;
    // 最大密钥长度
    private Integer maxKeySize = 1024;
}