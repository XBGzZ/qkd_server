package com.xbg.qkd_server.infrastructure.keyManager.cache;

import com.xbg.qkd_server.common.errors.NotSupportException;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author XBG
 * @description: TODO
 * @date 2025/1/22 19:12
 */
public class SimpleCache implements KeyEntityCache {

    Map<String, KeyEntity> cache = new ConcurrentHashMap<>();

    // 密钥总数
    private final AtomicInteger keyTotalCount = new AtomicInteger(0);

    // 链表读写锁
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    // 密钥顺序记录链表
    private final List<KeyEntity> keyList = new ArrayList<>();

    private Integer maxKeyCount;

    /**
     * 乐观锁，确保线程安全
     * @param count
     * @return
     */
    public Boolean preAllocateCache(Integer count) {

        int nextTotalCount = 0;
        do {
            nextTotalCount = keyTotalCount.get() + count;
            if (nextTotalCount > maxKeyCount) {
                return false;
            }
        } while (keyTotalCount.compareAndSet(count, nextTotalCount));
        return true;
    }

    @Override
    public KeyEntity queryEntity(String keyId) {
        return cache.get(keyId);
    }

    @Override
    public List<KeyEntity> acquireEntity(Integer count) {
        return null;
    }

    @Override
    public List<KeyEntity> removeByOwner(String ownerId) {
        throw new NotSupportException();
    }

    @Override
    public KeyEntity removeByKeyId(String keyId) {
        KeyEntity keyEntity = cache.get(keyId);
        cache.remove(keyId);
        return keyEntity;
    }

    @Override
    public Boolean addEntity(KeyEntity keyEntity) {
        cache.put(keyEntity.getKeyId(), keyEntity);
        return true;
    }

    @Override
    public Boolean addEntity(Collection<KeyEntity> keyEntity) {
        return null;
    }

    @Override
    public Boolean release(Integer count) {
        return null;
    }

    @Override
    public Boolean release() {
        return null;
    }

    @Override
    public Integer maxCacheSize() {
        return 0;
    }

    @Override
    public Integer usedCacheSize() {
        return 0;
    }

    /**
     * 密钥记录
     * @param keys
     */
    protected void keyListRecord(Collection<KeyEntity> keys) {
        lock.writeLock().lock();
        keyList.addAll(keys);
        lock.writeLock().unlock();
    }

    /**
     * 移除最早记录的密钥
     * @param count
     * @return
     */
    protected List<KeyEntity> removeFirstRecord(Integer count) {
        List<KeyEntity> removeKeys = peekFirstRecord(count);
        lock.writeLock().lock();
        keyList.subList(0, count).clear();
        lock.writeLock().unlock();
        return removeKeys;
    }

    /**
     * 查看最早记录的密钥
     * @param count
     * @return
     */
    protected List<KeyEntity> peekFirstRecord(Integer count) {
        List<KeyEntity> keys;
        lock.readLock().lock();
        keys = keyList.stream().limit(count).toList();
        lock.readLock().unlock();
        return keys;
    }
}
