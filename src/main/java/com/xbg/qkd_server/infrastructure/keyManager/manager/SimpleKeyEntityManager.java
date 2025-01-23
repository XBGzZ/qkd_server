package com.xbg.qkd_server.infrastructure.keyManager.manager;

import com.xbg.qkd_server.infrastructure.keyManager.cache.SimpleCache;
import com.xbg.qkd_server.infrastructure.keyManager.config.BaseKeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.factory.SimpleKeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.states.IManagerState;

/**
 * @author XBG
 * @description: 密钥实体模板类
 * @date 2025/1/14 22:40
 */
public class SimpleKeyEntityManager extends AbstractBaseKeyEntityManager<SimpleCache, SimpleKeyEntityFactory, BaseKeyManagerConfig> {

    public SimpleKeyEntityManager(SimpleCache allocatedKeys, BaseKeyManagerConfig config, SimpleKeyEntityFactory keyEntityFactory) {
        super(allocatedKeys, config, keyEntityFactory);
    }

    @Override
    public IManagerState managerState() {
        return null;
    }

    protected Integer releaseKeys(Integer count) {
        return count;
    }

}
