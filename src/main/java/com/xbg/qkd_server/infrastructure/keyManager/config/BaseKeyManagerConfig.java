package com.xbg.qkd_server.infrastructure.keyManager.config;

import lombok.Data;

/**
 * @author XBG
 * @description: 密钥管理器配置文件
 * @date 2025/1/1 13:38
 */
@Data
public class BaseKeyManagerConfig {
    // 最大密钥数量
    private Integer maxKeyCount;

    // 密钥默认长度
    private Integer keySize;

    // 最大密钥长度
    private Integer maxKeySize;

    // 最小密钥长度
    private Integer minKeySize;

    // 单次可申请密钥最大数量
    private Integer maxKeyPerRequest;
}
