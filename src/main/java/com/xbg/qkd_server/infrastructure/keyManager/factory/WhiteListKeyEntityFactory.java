package com.xbg.qkd_server.infrastructure.keyManager.factory;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.keyEntity.WhiteListKeyEntity;
import org.springframework.context.annotation.Primary;

import java.util.Objects;
import java.util.Optional;

/**
 * @author XBG
 * @description: 白名单密钥工厂
 * @date 2025/1/14 0:49
 */
@Primary
public class WhiteListKeyEntityFactory extends BaseKeyEntityFactory {

    BaseKeyEntityFactory baseKeyEntityFactory;

    public Boolean initialize(BaseKeyEntityFactory baseKeyEntityFactory) {
        if (baseKeyEntityFactory.getClass().isAssignableFrom(WhiteListKeyEntityFactory.class)) {
            return Boolean.FALSE;
        }
        this.baseKeyEntityFactory = baseKeyEntityFactory;
        return Boolean.TRUE;
    }

    @Override
    protected KeyEntity generateKeyEntity(String id, Integer keySize) {
        if (Objects.isNull(baseKeyEntityFactory)) {
            return null;
        }
        KeyEntity keyEntity = baseKeyEntityFactory.generateKeyEntity(id, keySize);
        return new WhiteListKeyEntity(keyEntity);
    }

    @Override
    public Optional<KeyEntity> produceKeyEntity(Integer keySize) {
        return baseKeyEntityFactory.produceKeyEntity(keySize);
    }
}
