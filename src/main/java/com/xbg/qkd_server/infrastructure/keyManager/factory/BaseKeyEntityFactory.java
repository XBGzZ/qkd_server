package com.xbg.qkd_server.infrastructure.keyManager.factory;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.KeyFactory;

/**
 * @author XBG
 * @description: 简单的密钥实体工厂
 * @date 2025/1/1 22:47
 */
public abstract class BaseKeyEntityFactory implements KeyFactory {
    @Override
    public KeyEntity GenerateKeyEntity() {
        return null;
    }
}
