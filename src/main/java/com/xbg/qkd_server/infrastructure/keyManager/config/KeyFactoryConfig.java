package com.xbg.qkd_server.infrastructure.keyManager.config;

import com.xbg.qkd_server.infrastructure.keyManager.factory.strategy.FactoryStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static com.xbg.qkd_server.common.constant.ConfigConstants.CONFIG_PREFIX_KEY_FACTORY_CONFIG;

/**
 * @author XBG
 * @description: TODO
 * @date 2025/1/21 0:36
 */
@Data
@Configuration
@ConfigurationProperties(CONFIG_PREFIX_KEY_FACTORY_CONFIG)
public class KeyFactoryConfig {
    // 工厂策略
    private FactoryStrategy strategy = FactoryStrategy.SIMPLE_FACTORY;
    // 最小密钥长度
    private Integer minKeySize = 128;
    // 密钥默认长度
    private Integer keySize = 256;
    // 最大密钥长度
    private Integer maxKeySize = 1024;
}