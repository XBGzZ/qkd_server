package com.xbg.qkd_server.infrastructure.keyManager.factory;

import cn.hutool.core.util.IdUtil;
import com.xbg.qkd_server.common.tools.MathTool;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactoryConfig;
import com.xbg.qkd_server.infrastructure.keyManager.config.BaseKeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.config.KeyFactoryConfig;
import lombok.Getter;

import java.util.Optional;

/**
 * @author XBG
 * @description: 抽象工厂基类，提供基本方法
 * @date 2025/1/14 0:27
 */

public abstract class BaseKeyEntityFactory implements KeyEntityFactory {

    protected String generateKeyId() {
        return IdUtil.randomUUID();
    }

    abstract protected KeyEntity genrateKeyEntity(String id, Integer keySize);
}
