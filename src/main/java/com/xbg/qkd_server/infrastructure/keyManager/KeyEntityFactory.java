package com.xbg.qkd_server.infrastructure.keyManager;

import java.util.Optional;

/**
 * @author XBG
 * @description: 密钥工厂接口
 * @date 2025/1/1 13:14
 */
public interface KeyEntityFactory<T extends KeyEntityFactoryConfig> {
    Optional<KeyEntity> produceKeyEntity(T keyConfig);

    T getKeyConfig();
}
