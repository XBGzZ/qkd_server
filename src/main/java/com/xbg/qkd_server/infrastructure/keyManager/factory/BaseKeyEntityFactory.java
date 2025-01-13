package com.xbg.qkd_server.infrastructure.keyManager.factory;

import cn.hutool.core.util.IdUtil;
import com.xbg.qkd_server.common.tools.MathTool;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactoryConfig;

import java.util.Optional;

/**
 * @author XBG
 * @description: 抽象工厂基类，提供基本方法
 * @date 2025/1/14 0:27
 */
public abstract class BaseKeyEntityFactory<T extends KeyEntityFactoryConfig> implements KeyEntityFactory<T> {
    @Override
    public Optional<KeyEntity> produceKeyEntity(T keyConfig) {
        if (!MathTool.isEvenNumber(keyConfig.getKeySize()) || !MathTool.aIsDivisibleByN(keyConfig.getKeySize(), Byte.SIZE)) {
            return Optional.empty();
        }
        return Optional.of(genrateKeyEntity(generateKeyId(), keyConfig));
    }
    protected String generateKeyId() {
        return IdUtil.randomUUID();
    };

    abstract protected KeyEntity genrateKeyEntity(String id, T keySize);
}
