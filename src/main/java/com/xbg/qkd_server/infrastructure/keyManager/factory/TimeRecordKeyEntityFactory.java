package com.xbg.qkd_server.infrastructure.keyManager.factory;

import com.xbg.qkd_server.common.annotations.KeyFactory;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.keyEntity.TimeRecordKeyEntity;

/**
 * @author XBG
 * @description: 带时间记录功能的工厂
 * @date 2025/1/13 23:51
 */
@KeyFactory
public class TimeRecordKeyEntityFactory extends BaseSimpleKeyEntityFactory {

    @Override
    protected KeyEntity genrateKeyEntity(String id, SimpleFactoryConfig config) {
        return new TimeRecordKeyEntity(id, config.getKeySize());
    }
}
