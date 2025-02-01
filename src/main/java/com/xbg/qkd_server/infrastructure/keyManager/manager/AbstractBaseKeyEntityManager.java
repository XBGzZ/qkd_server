package com.xbg.qkd_server.infrastructure.keyManager.manager;

import com.xbg.qkd_server.common.enums.ErrorCode;
import com.xbg.qkd_server.common.enums.CommonCode;
import com.xbg.qkd_server.common.enums.KeyErrorCode;
import com.xbg.qkd_server.common.errors.KeyException;
import com.xbg.qkd_server.infrastructure.keyManager.*;
import com.xbg.qkd_server.infrastructure.keyManager.cache.KeyEntityCache;
import com.xbg.qkd_server.infrastructure.keyManager.config.KeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.factory.KeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.states.IManagerState;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  密钥实体类简单模板，在请求时生产密钥
 *  不会主动注入密钥
 * </pre>
 *
 * @author XBG
 * @date 2025-01-28 10:54
 */
public abstract class AbstractBaseKeyEntityManager<Cache extends KeyEntityCache, Factory extends KeyEntityFactory, Config extends KeyManagerConfig> implements KeyEntityManager {

    protected Cache cache;

    protected Factory factory;

    protected Config config;
    public AbstractBaseKeyEntityManager(Cache cache, Config config, Factory factory) {
        this.cache = cache;
        this.factory = factory;
        this.config = config;
    }

    @Override
    public abstract IManagerState<?> managerState();

    @Override
    public List<KeyEntity> queryAssignedKeyByKeyId(Set<String> keyIds) {
        return keyIds.stream()
                .map(cache::queryEntity).toList();
    }

    /**
     * 考虑性能问题，不支持此用法
     * @param saeId
     * @return
     */
    @Override
    public Boolean containTargetSAEKey(String saeId) {
        return cache.containSAEKey(saeId);
    }

    @Override
    public List<KeyEntity> acquireKeys(String owner, Integer count, Integer size) throws KeyException {
        ErrorCode errorCode = acquireValid(owner,count,size);
        if(errorCode != CommonCode.SUCCESS) {
            // 密钥参数异常，异常形式抛出
            throw new KeyException(errorCode);
        }
        // 从缓存中申请密钥
        List<KeyEntity> cachedKeyEntity = Optional.ofNullable(cache.acquireEntity(owner, count, size))
                .orElse(List.of());
        // 如果缓存可提供足够数量的密钥，则直接返回
        if (count.equals(cachedKeyEntity.size())) {
            return cachedKeyEntity;
        }
        // 告知缓存需要申请空间，如果申请成功，则开始生产密钥
        if (!cache.preAllocateCache(count)) {
            throw new KeyException(KeyErrorCode.KEY_PRE_ALLOCATE_FAILED);
        }
        // 待分配密钥数量
        int remainKeys = count - Optional.of(cachedKeyEntity).orElse(List.of()).size();
        var newKeyEntity = Stream.iterate(0, i -> i + 1)
                .limit(remainKeys)
                .map(i -> size)
                .map(factory::produceKeyEntity)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(keyEntity -> keyEntity.setOwner(owner))
                .toList();
        if(!cache.addEntity(newKeyEntity)) {
            throw new KeyException(KeyErrorCode.KEY_DUPLICATE_KEY_ENTITY_ID);
        }
        ArrayList<KeyEntity> resultList = new ArrayList<>(cachedKeyEntity.size() + newKeyEntity.size());
        resultList.addAll(cachedKeyEntity);
        resultList.addAll(newKeyEntity);
        return resultList;
    }

    protected abstract ErrorCode acquireValid(String owner, Integer count, Integer size);
}
