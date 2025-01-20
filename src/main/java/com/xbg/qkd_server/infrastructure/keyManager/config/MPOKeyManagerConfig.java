package com.xbg.qkd_server.infrastructure.keyManager.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author XBG
 * @description: Memory Placement Optimization (MPO)
 * 内存优化版 keyManage配置文件
 * @date 2025/1/12 22:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
@ConfigurationProperties(prefix = "key-manager")
public class MPOKeyManagerConfig extends BaseKeyManagerConfig {
    // 密钥存活时间，0代表关闭
    private Long keyLiveTime;

    // 密钥缓存桶数量，最佳分配桶 数量 为 3个
    private Integer keyCacheBuckets;
}
