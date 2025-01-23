package com.xbg.qkd_server.infrastructure.keyManager.manager;

import com.xbg.qkd_server.infrastructure.keyManager.cache.SimpleCache;
import com.xbg.qkd_server.infrastructure.keyManager.config.BaseKeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.factory.SimpleKeyEntityFactory;

/**
 * @author XBG
 * @description: 内存优化的密钥管理器
 * @date 2025/1/14 22:48
 */
public abstract class MPOKeyEntityManager extends AbstractBaseKeyEntityManager<SimpleCache, SimpleKeyEntityFactory,BaseKeyManagerConfig> {

    public MPOKeyEntityManager(SimpleCache allocatedKeys, BaseKeyManagerConfig config, SimpleKeyEntityFactory keyEntityFactory) {
        super(allocatedKeys, config, keyEntityFactory);
    }
}
