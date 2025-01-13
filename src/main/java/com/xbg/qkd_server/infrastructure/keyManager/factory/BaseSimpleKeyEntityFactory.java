package com.xbg.qkd_server.infrastructure.keyManager.factory;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactoryConfig;
import lombok.Data;

/**
 * @author XBG
 * @description: 分类抽象类
 * @date 2025/1/14 1:39
 */
public abstract class BaseSimpleKeyEntityFactory extends BaseKeyEntityFactory<BaseSimpleKeyEntityFactory.SimpleFactoryConfig> {
    @Data
    public static class SimpleFactoryConfig implements KeyEntityFactoryConfig {
        private Integer keySize = 0;
    }

    @Override
    public SimpleFactoryConfig getKeyConfig() {
        return new SimpleFactoryConfig();
    }
}
