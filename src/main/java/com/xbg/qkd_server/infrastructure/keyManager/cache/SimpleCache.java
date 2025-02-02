package com.xbg.qkd_server.infrastructure.keyManager.cache;

import com.xbg.qkd_server.common.errors.KeyException;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author XBG
 * @description: TODO
 * @date 2025/1/22 19:12
 */
@Slf4j
public class SimpleCache implements KeyEntityCache {

    /**
     * 未分配密钥
     */
    private final Map<String, KeyEntity> unAllocatedKey = new ConcurrentHashMap<>();

    /**
     * 已分配密钥
     */
    private final Map<String, KeyEntity> allocatedKey = new ConcurrentHashMap<>();

    /**
     * 已分配密钥所属关系
     */
    private final ConcurrentHashMap<String, Set<String>> saeKeyRelation = new ConcurrentHashMap<>();

    /**
     * 顺序记录链表的读写锁
     */
    private final ReentrantReadWriteLock orderListLock = new ReentrantReadWriteLock(true);

    /**
     * 顺序链表，用于记录密钥添加顺序，方便按序销毁
     */
    private final List<KeyEntity> keyEntityOrderList = new LinkedList<>();

    /**
     * 密钥总数，必须使用内部提供的函数，确保线程安全
     * 由于工厂和缓存隔离，因此生产密钥前必须进行预分配，避免生产出密钥后无法放入容器中
     */
    private AtomicInteger keyTotalCount = new AtomicInteger(0);

    /**
     * 容器最大密钥上限
     */
    final private Integer maxKeyCount;

    public SimpleCache(Integer maxKeyCount) {
        this.maxKeyCount = maxKeyCount;
    }

    /**
     * 乐观锁，确保线程安全
     *
     * @param count
     * @return
     */
    public Boolean preAllocateCache(Integer count) {
        return totalKeyAdd(count);
    }

    @Override
    public KeyEntity queryEntity(String keyId) {
        return allocatedKey.get(keyId);
    }

    /**
     * 申请密钥
     * @param count
     * @return
     */
    @Override
    public List<KeyEntity> acquireEntity(String saeId, Integer count, Integer size) throws KeyException {
        if (unAllocatedKey.size() < count || count <= 0) {
            return List.of();
        }
        List<KeyEntity> keyEntities = null;
        synchronized (unAllocatedKey) {
            if (unAllocatedKey.size() < count) {
                return List.of();
            }
            keyEntities = unAllocatedKey.values().stream()
                    .filter(key -> key.getKeySize().equals(size))
                    .limit(count)
                    .toList();
            if (!count.equals(keyEntities.size())){
                return List.of();
            }
            keyEntities.stream()
                    .peek(key->key.setOwner(saeId))
                    .map(KeyEntity::getKeyId)
                    .forEach(unAllocatedKey::remove);
        }
        // 重新添加密钥
        if (!addEntity(keyEntities)) {
            return List.of();
        }
        return keyEntities;
    }

    /**
     * @param ownerId
     * @return
     */
    @Override
    public List<KeyEntity> removeByOwner(String ownerId) {
        Set<String> keyIds = saeKeyRelation.remove(ownerId);
        if (Objects.isNull(keyIds) || keyIds.isEmpty()) {
            return List.of();
        }
        return keyIds.stream().map(this::removeByKeyId).filter(Objects::nonNull).toList();
    }

    @Override
    public KeyEntity removeByKeyId(String keyId) {
        KeyEntity keyEntity = allocatedKey.get(keyId);
        allocatedKey.remove(keyId);
        if (!removeKeyFromOrder(keyEntity)) {
            log.error("remove key [{}] failed", keyId);
        }
        return keyEntity;
    }

    @Override
    public Boolean addEntity(KeyEntity keyEntity) {
        return addEntity(List.of(keyEntity));
    }

    @Override
    public Boolean addEntity(Collection<KeyEntity> keyEntity) {
        // 分类
        final List<KeyEntity> unallocatedList = new ArrayList<>();
        final List<KeyEntity> allocatedList = new ArrayList<>();
        keyEntity.forEach(item -> {
            if (StringUtils.hasLength(item.getOwner())) {
                allocatedList.add(item);
            } else {
                unallocatedList.add(item);
            }
        });
        if (!addNoOwnerKeyEntity(unallocatedList)) {
            log.error("add failed");
            return false;
        }
        if (!addHasOwnerKeyEntity(allocatedList)) {
            log.error("add failed");
            return false;
        }
        return true;
    }

    /**
     * 添加已分配的密钥
     * 认为冲突的概率很低
     * 添加未分配的密钥，只能是由工厂主动推送的密钥
     * 主动推送的密钥可以交给容器托管
     *
     * @param keyEntityList
     */
    protected Boolean addNoOwnerKeyEntity(Collection<KeyEntity> keyEntityList) {
        return addKeyEntityToCacheMap(keyEntityList, unAllocatedKey);
    }

    /**
     * 添加已经分配的密钥
     * 这种只能是由SAE申请，然后Manager设置Onwer后
     * 交给cache管理
     *
     * @param keyEntityList
     */
    protected Boolean addHasOwnerKeyEntity(Collection<KeyEntity> keyEntityList) {
        Boolean isSuccess = addKeyEntityToCacheMap(keyEntityList, allocatedKey);
        if (!isSuccess) {
            return false;
        }
        // 记录映射关系
        for (var item : keyEntityList) {
            Set<String> keyIds = saeKeyRelation.putIfAbsent(item.getKeyId(), new ConcurrentSkipListSet<>());
            if (Objects.isNull(keyIds)) {
                keyIds = saeKeyRelation.get(item.getKeyId());
            }
            keyIds.add(item.getKeyId());
        }
        keyListRecord(keyEntityList);
        return true;
    }

    private Boolean addKeyEntityToCacheMap(Collection<KeyEntity> keyEntityList, Map<String, KeyEntity> cacheMap) {
        if (Objects.isNull(keyEntityList) || Objects.isNull(cacheMap) || keyEntityList.isEmpty()) {
            return true;
        }
        boolean isConflict = false;
        for (var item : keyEntityList) {
            if (cacheMap.containsKey(item.getKeyId())) {
                isConflict = true;
                break;
            }
            cacheMap.put(item.getKeyId(), item);
        }
        if (isConflict) {
            keyEntityList.stream().map(KeyEntity::getKeyId).forEach(cacheMap::remove);
            return false;
        }
        return true;
    }

    @Override
    public Integer releaseAll(Integer count) {
        return removeFirstRecord(count).size();
    }

    @Override
    public Integer releaseAll() {
        var size = keyEntityOrderList.size();
        removeFirstRecord(size);
        return size;
    }

    @Override
    public Integer maxCacheSize() {
        return maxKeyCount;
    }

    @Override
    public Integer usedCacheSize() {
        return keyTotalCount.get();
    }

    @Override
    public Boolean containSAEKey(String saeId) {
        return saeKeyRelation.containsKey(saeId);
    }

    /**
     * 密钥记录
     *
     * @param keys
     */
    protected void keyListRecord(Collection<KeyEntity> keys) {
        orderListLock.writeLock().lock();
        keyEntityOrderList.addAll(keys);
        orderListLock.writeLock().unlock();
    }

    /**
     * 记录一把密钥
     *
     * @param keys
     */
    protected void keyListRecord(KeyEntity keys) {
        orderListLock.writeLock().lock();
        keyEntityOrderList.add(keys);
        orderListLock.writeLock().unlock();
    }

    /**
     * 移除最早记录的密钥
     *
     * @param count
     * @return
     */
    protected List<KeyEntity> removeFirstRecord(Integer count) {
        List<KeyEntity> removeKeys = peekFirstRecord(count);
        orderListLock.writeLock().lock();
        keyEntityOrderList.subList(0, count).clear();
        orderListLock.writeLock().unlock();
        totalKeyDecrease(removeKeys.size());
        return removeKeys;
    }

    /**
     * 查看最早记录的密钥
     *
     * @param count
     * @return
     */
    protected List<KeyEntity> peekFirstRecord(Integer count) {
        List<KeyEntity> keys;
        orderListLock.readLock().lock();
        keys = keyEntityOrderList.stream().limit(count).toList();

        orderListLock.readLock().unlock();
        return keys;
    }

    /**
     * 总数增加
     *
     * @param count
     * @return
     */
    protected Boolean totalKeyAdd(Integer count) {
        int nextTotalCount = 0;
        int expectCount = 0;
        do {
            expectCount = keyTotalCount.get();
            if (expectCount == maxKeyCount) {
                return false;
            }
            nextTotalCount = expectCount + count;
            if (nextTotalCount > maxKeyCount || nextTotalCount < 0) {
                return false;
            }
        } while (!keyTotalCount.compareAndSet(expectCount, nextTotalCount));
        return true;
    }

    /**
     * 总数减少
     *
     * @param count
     * @return
     */
    protected Boolean totalKeyDecrease(Integer count) {
        return totalKeyAdd(-count);
    }

    protected Boolean removeKeyFromOrder(KeyEntity keyEntity) {
        orderListLock.writeLock().lock();
        boolean remove = keyEntityOrderList.remove(keyEntity);
        orderListLock.writeLock().unlock();
        return remove;
    }
}
