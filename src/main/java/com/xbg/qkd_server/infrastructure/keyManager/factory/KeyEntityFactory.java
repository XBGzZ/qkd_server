package com.xbg.qkd_server.infrastructure.keyManager.factory;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;

import java.util.Optional;

/**
 * @author XBG
 * @description: 密钥工厂接口
 * @date 2025/1/1 13:14
 */
public interface KeyEntityFactory {
    Optional<KeyEntity> produceKeyEntity(Integer keySize);
}
