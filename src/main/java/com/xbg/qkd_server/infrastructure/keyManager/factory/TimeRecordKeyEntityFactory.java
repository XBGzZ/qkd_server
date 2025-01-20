package com.xbg.qkd_server.infrastructure.keyManager.factory;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.config.KeyFactoryConfig;
import com.xbg.qkd_server.infrastructure.keyManager.keyEntity.TimeRecordKeyEntity;

/**
 * @author XBG
 * @description: 带时间记录功能的工厂
 * @date 2025/1/13 23:51
 */
public class TimeRecordKeyEntityFactory extends SimpleBaseEntityFactory {

    public TimeRecordKeyEntityFactory(KeyFactoryConfig config) {
        super(config);
    }

    @Override
    protected KeyEntity genrateKeyEntity(String id, Integer keySize) {
        return new TimeRecordKeyEntity(id, keySize);
    }
}
