package com.xbg.qkd_server.infrastructure.keyManager.impl;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import lombok.Builder;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author XBG
 * @description: 基本密钥对象
 * @date 2025/1/1 22:27
 */
public class SimpleKeyEntity implements KeyEntity {
    // 密钥Id
    private final String id;
    // 密钥本体
    private final String key;
    // 所属 SAE
    private String owner;
    // 分配时间
    private Long allocateTime;

    public SimpleKeyEntity(String id, String key) {
        this.id = id;
        this.key = key;
    }

    @Override
    public Boolean isUsing() {
        return StringUtils.hasLength(owner);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getKeyId() {
        return id;
    }

    @Override
    public Boolean setOwner(String owner) {
        if (this.owner.equals(owner)) {
            return true;
        }
        if (StringUtils.hasLength(owner)) {
            return false;
        }
        this.owner = owner;
        recordAllocateTime();
        return true;
    }

    @Override
    public Long getAllocateTime() {
        return allocateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SimpleKeyEntity that = (SimpleKeyEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key);
    }

    private void recordAllocateTime() {
        this.allocateTime = System.currentTimeMillis();
    }
}
