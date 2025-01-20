package com.xbg.qkd_server.infrastructure.keyManager.manager;

import com.xbg.qkd_server.common.errors.NotSupportException;
import com.xbg.qkd_server.infrastructure.keyManager.*;
import com.xbg.qkd_server.infrastructure.keyManager.config.BaseKeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.states.IManagerState;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * @author XBG
 * @description: 密钥实体基类，负责最基本的管理机制
 * @date 2025/1/14 22:40
 */
public abstract class BaseKeyEntityManager<T extends IManagerState, C extends BaseKeyManagerConfig> implements KeyEntityManager<T> {

    // 密钥缓存容器
    private Map<String, KeyEntity> allocatedKeys;

    // 配置文件
    private C config;

    // 密钥总数
    private final AtomicInteger keyTotalCount = new AtomicInteger(0);

    // 链表读写锁
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    // 密钥顺序记录链表
    private final List<KeyEntity> keyList = new ArrayList<>();

    @Setter
    KeyEntityFactory keyEntityFactory;

    protected Boolean initialize(C config) {
        if (!loadConfig(config)) {
            return false;
        }
        if (!initKeyCache(config)) {
            return false;
        }
        if (initKeyFactory(config)) {
            return false;
        }
        return true;
    }


    // 配置加载
    private Boolean loadConfig(C config) {
        if (Objects.isNull(config)) {
            return false;
        }
        this.config = config;
        return true;
    }

    // 密钥工厂初始化
    protected Boolean initKeyFactory(C config) {
        if (Objects.isNull(config)) {
            return false;
        }
        keyEntityFactory = KeyEntityFactoryFactory(config);
        return Objects.nonNull(keyEntityFactory);
    }

    // 密钥工厂工厂方法
    protected abstract KeyEntityFactory KeyEntityFactoryFactory(C config);

    // 密钥缓存初始化
    protected Boolean initKeyCache(C config) {
        this.allocatedKeys = keyCacheFactory(config);
        return Objects.nonNull(this.allocatedKeys);
    }

    // 密钥缓存工厂
    protected Map<String, KeyEntity> keyCacheFactory(C config) {
        return new ConcurrentHashMap<>();
    }


    @Override
    public Set<KeyEntity> queryAssignedKeyByKeyId(Set<String> keyIds) {
        return keyIds.stream().map(allocatedKeys::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public List<KeyEntity> findAssignedKeyBySaeId(String saeId) {
        throw new NotSupportException();
    }

    /**
     * 记录已经分配密钥
     *
     * @param keys
     * @return
     */
    protected void recordAllocateKeys(Collection<KeyEntity> keys) {
        keys.forEach((keyEntity -> {
            allocatedKeys.put(keyEntity.getKeyId(), keyEntity);
        }));
    }

    /**
     * 校验请求是否合法
     *
     * @param count
     * @param size
     * @return
     */
    protected Boolean isAcquireValid(Integer count, Integer size) {
        if (count > config.getMaxKeyPerRequest()) {
            return false;
        }
        return size <= config.getKeyFactoryConfig().getMaxKeySize() && size >= config.getKeyFactoryConfig().getMinKeySize();
    }

    /**
     * 密钥记录
     *
     * @param keys
     */
    protected void keyListRecord(Collection<KeyEntity> keys) {
        lock.writeLock().lock();
        keyList.addAll(keys);
        lock.writeLock().unlock();
    }

    /**
     * 移除最早记录的密钥
     *
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
     *
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

    /**
     * 密钥数量预分配
     *
     * @param count
     * @return
     */
    protected Boolean preAllocateKeys(Integer count) {
        int nextTotalCount = 0;
        do {
            nextTotalCount = keyTotalCount.get() + count;
            if (nextTotalCount > config.getMaxKeyPerRequest() && nextTotalCount >= 0) {
                return false;
            }
        } while (keyTotalCount.compareAndSet(count, nextTotalCount));
        return true;
    }

    /**
     * 生产密钥
     *
     * @param count
     * @param size
     * @return
     */
    protected List<KeyEntity> doGenerateKey(Integer count, Integer size) {
        if (!preAllocateKeys(count)) {
            return Collections.emptyList();
        }
        List<KeyEntity> newKeys = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Optional<KeyEntity> keyEntity = keyEntityFactory.produceKeyEntity(size);
            keyEntity.ifPresent(newKeys::add);
        }
        keyListRecord(newKeys);
        return newKeys;
    }

    /**
     * 外部请求密钥
     *
     * @param owner
     * @param count
     * @param size
     * @return
     */
    @Override
    public List<KeyEntity> acquireKeys(String owner, Integer count, Integer size) {
        if (!isAcquireValid(count, size)) {
            return List.of();
        }
        List<KeyEntity> newKeys = doGenerateKey(count, size);
        recordAllocateKeys(newKeys);
        return newKeys;
    }

    protected Integer releaseKeys(Integer count) {
        if (!preAllocateKeys(-count)) {
            return 0;
        }
        removeFirstRecord(count);
        return count;
    }

}
