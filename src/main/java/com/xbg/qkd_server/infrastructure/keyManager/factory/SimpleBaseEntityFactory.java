package com.xbg.qkd_server.infrastructure.keyManager.factory;

import com.xbg.qkd_server.common.enums.ErrorCode;
import com.xbg.qkd_server.common.enums.CommonCode;
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
        for (Integer size : sizeConfigs) {
            if (sizeValueCheck(size) != CommonCode.SUCCESS) {
                throw new KeyException(KeyErrorCode.KEY_SIZE_CONFIG_INVALID);
            }
        }
        this.config = config;
    }

    @Override
    public Optional<KeyEntity> produceKeyEntity(Integer keySize) {
        var errorCode = isKeySizeValid(keySize);
        if (isKeySizeValid(keySize) != CommonCode.SUCCESS) {
            throw new KeyException(errorCode);
        }
        return Optional.of(generateKeyEntity(generateKeyId(), keySize));
    }

    protected ErrorCode isKeySizeValid(Integer keySize) {
        ErrorCode errorCode = sizeRangeCheck(keySize);
        if (errorCode != CommonCode.SUCCESS) {
            return errorCode;
        }
        return sizeValueCheck(keySize);
    }

    protected ErrorCode sizeRangeCheck(Integer keySize) {
        if (keySize > config.getMaxKeySize()) {
            return KeyErrorCode.KEY_SIZE_TOO_LONG;
        }
        if (keySize < config.getMinKeySize()) {
            return KeyErrorCode.KEY_SIZE_TOO_SHORT;
        }
        return CommonCode.SUCCESS;
    }

    protected ErrorCode sizeValueCheck(Integer keySize) {
        if (MathTool.isEvenNumber(keySize) && MathTool.aIsDivisibleByN(keySize, Byte.SIZE)) {
            return CommonCode.SUCCESS;
        }
        return KeyErrorCode.KEY_SIZE_INVALID;
    }
}
