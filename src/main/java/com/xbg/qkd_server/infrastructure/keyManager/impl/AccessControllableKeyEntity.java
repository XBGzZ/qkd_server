package com.xbg.qkd_server.infrastructure.keyManager.impl;

import org.springframework.util.StringUtils;

/**
 * @Author XBG
 * @Description: 抽象基类，具有访问控制能力的key实例
 * @Date 2025-01-05
 */

public abstract class AccessControllableKeyEntity extends SimpleKeyEntity implements KeyAccessControl {

    // 密钥拥有者
    private String owner;

    AccessControllableKeyEntity(String Id, String key, String owner) {
        super(Id, key, owner);
    }

    @Override
    public Boolean setOwner(String saeId) {
        if (!StringUtils.hasLength(owner)) {
            owner = saeId;
            return true;
        }
        return false;
    }

    @Override
    public String getOwner() {
        return owner;
    }
}
