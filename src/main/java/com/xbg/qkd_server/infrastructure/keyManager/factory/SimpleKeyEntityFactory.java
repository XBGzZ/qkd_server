package com.xbg.qkd_server.infrastructure.keyManager.factory;

import com.xbg.qkd_server.common.annotations.KeyFactory;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.keyEntity.SimpleKeyEntity;

/**
 * @author XBG
 * @description: 简单密钥工厂
 * @date 2025/1/13 23:51
 */
@KeyFactory
public class SimpleKeyEntityFactory extends BaseSimpleKeyEntityFactory {
    @Override
    protected KeyEntity genrateKeyEntity(String id, SimpleFactoryConfig config) {
        return new SimpleKeyEntity(id, config.getKeySize());
    }
}
