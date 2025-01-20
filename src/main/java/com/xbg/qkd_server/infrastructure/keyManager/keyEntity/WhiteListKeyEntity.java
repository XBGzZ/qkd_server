package com.xbg.qkd_server.infrastructure.keyManager.keyEntity;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import lombok.Builder;

import java.util.Set;

/**
 * @Author XBG
 * @Description: 具有白名单机制的密钥实体对象
 * owner：密钥所属对象，密钥申请者
 * saeIdWhiteList：白名单
 * whiteListSwitch：白名单开关，白名单必须拥有值的时候才能开启
 * @Date 2025-01-05
 */
public class WhiteListKeyEntity implements WhiteListAbility, KeyEntity {

    private final KeyEntity keyEntity;
    // 可访问白名单
    private Set<String> saeIdWhiteList;
    // 访问控制开关
    private Boolean whiteListSwitch;

    public WhiteListKeyEntity(KeyEntity keyEntity) {
        this.keyEntity = keyEntity;
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
        enableWhiteList();
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

    @Override
    public Boolean isUsing() {
        return keyEntity.isUsing();
    }

    @Override
    public String getKey() {
        return keyEntity.getKey();
    }

    @Override
    public String getKeyId() {
        return keyEntity.getKeyId();
    }

    @Override
    public Boolean setOwner(String owner) {
        return keyEntity.setOwner(owner);
    }

    @Override
    public String getOwner() {
        return keyEntity.getOwner();
    }

    @Override
    public Long getAllocateTime() {
        return keyEntity.getAllocateTime();
    }


}
