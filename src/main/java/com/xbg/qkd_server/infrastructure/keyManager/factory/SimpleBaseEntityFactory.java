package com.xbg.qkd_server.infrastructure.keyManager.factory;

import com.xbg.qkd_server.common.enums.KeyErrorCode;
import com.xbg.qkd_server.common.errors.KeyException;
import com.xbg.qkd_server.common.tools.MathTool;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.config.KeyFactoryConfig;
import lombok.Getter;

import java.util.List;
import java.util.Optional;


/**
 * @author XBG
 * @description: TODO
 * @date 2025/1/21 0:38
 */
public abstract class SimpleBaseEntityFactory extends BaseKeyEntityFactory {

    @Getter
    private final KeyFactoryConfig config;

    public SimpleBaseEntityFactory(KeyFactoryConfig config) {
        List<Integer> sizeConfigs = List.of(config.getKeySize(), config.getMaxKeySize(), config.getMinKeySize());
        for(Integer size : sizeConfigs) {
            if(!sizeValueCheck(size)) {
                throw new KeyException(KeyErrorCode.KEY_SIZE_CONFIG_INVALID);
            }
        }
        this.config = config;
    }

    @Override
    public Optional<KeyEntity> produceKeyEntity(Integer keySize) {
        if (!isKeySizeValid(keySize)) {
            return Optional.empty();
        }
        return Optional.of(genrateKeyEntity(generateKeyId(), keySize));
    }

    protected boolean isKeySizeValid(Integer keySize) {
        return sizeRangeCheck(keySize) && sizeValueCheck(keySize);
    }

    protected boolean sizeRangeCheck(Integer keySize) {
        return keySize <= config.getMaxKeySize() && keySize >= config.getMinKeySize();
    }

    protected boolean sizeValueCheck(Integer keySize) {
        return MathTool.isEvenNumber(keySize) && MathTool.aIsDivisibleByN(keySize, Byte.SIZE);
    }
}
