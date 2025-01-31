package com.xbg.qkd_server.infrastructure.keyManager.config;

import com.xbg.qkd_server.infrastructure.keyManager.manager.strategy.KeyManagerStrategy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author XBG
 * @description: 密钥管理器配置文件
 * @date 2025/1/1 13:38
 */
@Data
public class BaseKeyManagerConfig implements KeyManagerConfig {
    // 配置KME的id
    private String kmeId = "Bob";

    // 密钥管理器策略
    private KeyManagerStrategy strategy = KeyManagerStrategy.SIMPLE_KEY_MANAGER;

    // 最大密钥数量
    private Integer maxKeyCount = 1000;

    // 单次可申请密钥最大数量
    private Integer maxKeyPerRequest = 16;

    // saeId上限
    private Integer maxSaeIdCount = 6;

    // 工厂配置
    @Autowired
    private KeyFactoryConfig keyFactoryConfig;
}
