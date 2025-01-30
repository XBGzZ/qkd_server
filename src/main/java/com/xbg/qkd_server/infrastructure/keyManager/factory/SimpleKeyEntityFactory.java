package com.xbg.qkd_server.infrastructure.keyManager.factory;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.config.KeyFactoryConfig;
import com.xbg.qkd_server.infrastructure.keyManager.keyEntity.SimpleKeyEntity;

/**
 * @author XBG
 * @description: 简单密钥工厂
 * @date 2025/1/13 23:51
 */
public class SimpleKeyEntityFactory extends SimpleBaseEntityFactory {
    public SimpleKeyEntityFactory(KeyFactoryConfig config) {
        super(config);
    }

    @Override
    protected KeyEntity generateKeyEntity(String id, Integer keySize) {
        return new SimpleKeyEntity(id, keySize);
    }
}
