package com.xbg.qkd_server.infrastructure.keyManager.impl;

import java.util.Set;

/**
 * @Author XBG
 * @Description: 具有白名单机制的密钥实体对象
 *               owner：密钥所属对象，密钥申请者
 *               saeIdWhiteList：白名单
 *               whiteListSwitch：白名单开关，白名单必须拥有值的时候才能开启
 * @Date 2025-01-05
 */

public class WhiteListKeyEntity extends AccessControllableKeyEntity implements WhiteListAbility{
    // 可访问白名单
    private Set<String> saeIdWhiteList;
    // 访问控制开关
    private Boolean whiteListSwitch;

    WhiteListKeyEntity(String Id, String key, String owner) {
        super(Id, key, owner);
        disableWhiteList();
    }

    WhiteListKeyEntity(String id, String key, String owner, Set<String> saeIdWhiteList) {
        super(id, key, owner);
        this.saeIdWhiteList = saeIdWhiteList;
        enableWhiteList();
    }

    @Override
    public Boolean isAccessAble(String saeId) {
        if (!whiteListSwitch) {
            return true;
        }
        if (getOwner().equals(saeId)) {
            return true;
        }
        return saeIdWhiteList.contains(saeId);
    }

    @Override
    public Boolean addWhiteListSaeIds(Set<String> saeIds) {
        saeIdWhiteList.addAll(saeIds);
        return true;
    }

    @Override
    public Boolean removeWhiteListSaeIds(Set<String> saeIds) {
        saeIdWhiteList.removeAll(saeIds);
        if (saeIdWhiteList.isEmpty()) {
            disableWhiteList();
        }
        return true;
    }

    @Override
    public Boolean enableWhiteList() {
        if (saeIdWhiteList.isEmpty()) {
            return false;
        }
        whiteListSwitch = true;
        return true;
    }

    @Override
    public Boolean disableWhiteList() {
        whiteListSwitch = false;
        return true;
    }

    @Override
    public Boolean isWhiteListEnabled() {
        return whiteListSwitch;
    }
}
