package com.xbg.qkd_server.infrastructure.keyManager.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static com.xbg.qkd_server.common.constant.ConfigConstants.CONFIG_PREFIX_KEY_MANAGER_CONFIG;

/**
 * @author XBG
 * @description: Memory Placement Optimization (MPO)
 * 内存优化版 keyManage配置文件
 * @date 2025/1/12 22:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConfigurationProperties(prefix = CONFIG_PREFIX_KEY_MANAGER_CONFIG)
public class MPOKeyManagerConfig extends BaseKeyManagerConfig {
    // 密钥存活时间，0代表关闭
    private Long keyLiveTime = 0L;

    // 密钥缓存桶数量，最佳分配桶 数量 为 3个
    private Integer keyCacheBuckets = 3;
}
