package com.xbg.qkd_server.infrastructure.keyManager.factory;

import cn.hutool.core.util.IdUtil;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactory;


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
