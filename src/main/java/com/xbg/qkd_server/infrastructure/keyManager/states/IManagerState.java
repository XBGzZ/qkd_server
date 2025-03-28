package com.xbg.qkd_server.infrastructure.keyManager.states;

/**
 * @author XBG
 * @description: 标记接口
 * @date 2025/1/14 22:42
 */
public interface IManagerState<T extends Object> {

    Integer getDefaultKeySize();

    Integer getMaxKeyCount();

    Integer getStoredKeyCount();

    Integer getMaxKeyPerRequest();

    Integer getMaxKeySize();

    Integer getMinKeySize();

    Integer getMaxSAEIdCount();

    T getExtensionStatus();
}
