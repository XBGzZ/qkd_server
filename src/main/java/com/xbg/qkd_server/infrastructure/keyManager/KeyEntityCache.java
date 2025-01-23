package com.xbg.qkd_server.infrastructure.keyManager;

import java.util.Collection;
import java.util.List;

/**
 * @author XBG
 * @description: 密钥缓存接口
 * @date 2025/1/22 18:47
 */
public interface KeyEntityCache {
    /**
     * 查询密钥
     * @param keyId
     * @return
     */
    KeyEntity queryEntity(String keyId);

    /**
     * 请求密钥
     *
     * @param count
     * @return
     */
    List<KeyEntity> acquireEntity(Integer count);

    /**
     * 移除密钥 by owner
     * @param ownerId
     * @return
     */
    List<KeyEntity> removeByOwner(String ownerId);

    /**
     * 通过KeyId删除
     * @param keyId
     * @return
     */
    KeyEntity removeByKeyId(String keyId);

    /**
     * 添加密钥
     * @param keyEntity
     * @return
     */
    Boolean addEntity(KeyEntity keyEntity);

    /**
     * 预分配
     * @param count
     * @return
     */
    Boolean preAllocateCache(Integer count);

    /**
     * 批量添加
     * @param keyEntity
     * @return
     */
    Boolean addEntity(Collection<KeyEntity> keyEntity);

    /**
     * 按数量释放密钥
     * @param count
     * @return
     */
    Boolean release(Integer count);

    /**
     * 自动释放密钥
     * @return
     */
    Boolean release();

    /**
     * 获取最大缓存上限
     * @return
     */
    Integer maxCacheSize();

    /**
     * 获取已经使用的缓存空间
     * @return
     */
    Integer usedCacheSize();
}
