package com.xbg.qkd_server.infrastructure.keyManager;

import lombok.Data;

/**
 * @author XBG
 * @description: 密钥管理器配置文件
 * @date 2025/1/1 13:38
 */
@Data
public class KeyManagerConfig {

    // 密钥缓存数量
    private Integer keyCacheSize;

    // 密钥缓存容器数量
    private Integer keyCacheCount;

    // 密钥刷新周期
    private Integer keyRefreshTime;

}
