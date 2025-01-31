package com.xbg.qkd_server.infrastructure.keyManager.manager;

import com.xbg.qkd_server.common.enums.ErrorCode;
import com.xbg.qkd_server.common.enums.CommonCode;
import com.xbg.qkd_server.common.enums.KeyErrorCode;
import com.xbg.qkd_server.infrastructure.keyManager.cache.SimpleCache;
import com.xbg.qkd_server.infrastructure.keyManager.config.BaseKeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.factory.SimpleBaseEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.states.IManagerState;
import com.xbg.qkd_server.infrastructure.keyManager.states.ManagerState;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XBG
 * @description: 密钥实体管理器简单实现类
 * @date 2025/1/14 22:40
 */
public class SimpleKeyEntityManager extends AbstractBaseKeyEntityManager<SimpleCache, SimpleBaseEntityFactory, BaseKeyManagerConfig> {

    private final ConcurrentHashMap<String, Integer> saeKeyCounter = new ConcurrentHashMap<>();

    public SimpleKeyEntityManager(SimpleCache allocatedKeys, BaseKeyManagerConfig config, SimpleBaseEntityFactory keyEntityFactory) {
        super(allocatedKeys, config, keyEntityFactory);
    }

    @Override
    public IManagerState<?> managerState() {
        return ManagerState.builder()
                .maxKeyCount(config.getMaxKeyCount())
                .keySize(config.getKeyFactoryConfig().getKeySize())
                .maxKeySize(config.getKeyFactoryConfig().getMaxKeySize())
                .minKeySize(config.getKeyFactoryConfig().getMinKeySize())
                .maxKeyPerRequest(config.getMaxKeyPerRequest())
                .maxSAEIdCount(config.getMaxSaeIdCount())
                .storedKeyCount(cache.usedCacheSize())
                .build();
    }

    @Override
    protected ErrorCode acquireValid(String owner, Integer count, Integer size) {
        if (!StringUtils.hasLength(owner)){
            return KeyErrorCode.KEY_SAE_ID_IS_INVALID;
        }
        if ( !saeKeyCounter.containsKey(owner)) {
            synchronized (saeKeyCounter) {
                if (saeKeyCounter.size() > config.getMaxSaeIdCount()) {
                    return KeyErrorCode.KEY_TOTAL_COUNT_IS_OVER_CACHE_MAX_SIZE;
                }
            }
            saeKeyCounter.put(owner, 0);
        }
        if (count > config.getMaxKeyPerRequest() || count <= 0) {
            return count > config.getMaxKeyPerRequest() ? KeyErrorCode.KEY_OVER_PER_REQUEST_LIMIT : KeyErrorCode.KEY_COUNT_IS_NO_MORE_THAN_ZERO;
        }
        if (size > config.getKeyFactoryConfig().getMaxKeySize() || size < config.getKeyFactoryConfig().getMinKeySize()) {
            return size > config.getKeyFactoryConfig().getMaxKeySize() ? KeyErrorCode.KEY_SIZE_TOO_LONG : KeyErrorCode.KEY_SIZE_TOO_SHORT;
        }
        return CommonCode.SUCCESS;
    }

    @Override
    public Boolean unregisterSae(String saeId) {
        cache.removeByOwner(saeId);
        saeKeyCounter.remove(saeId);
        return true;
    }

    protected Integer releaseKeys(Integer count) {
        return cache.releaseAll(count);
    }

}
