package com.xbg.qkd_server.infrastructure.keyManager.config;

import com.xbg.qkd_server.infrastructure.keyManager.KeyManagerConfig;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author XBG
 * @description: 密钥管理器配置文件
 * @date 2025/1/1 13:38
 */
@Data
@Component
public class BaseKeyManagerConfig implements KeyManagerConfig {
    // 最大密钥数量
    private Integer maxKeyCount;

    // 单次可申请密钥最大数量
    private Integer maxKeyPerRequest;

    // 工厂配置
    private KeyFactoryConfig keyFactoryConfig = new KeyFactoryConfig();
}
