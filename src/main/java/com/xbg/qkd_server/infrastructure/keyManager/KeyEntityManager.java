package com.xbg.qkd_server.infrastructure.keyManager;

import com.xbg.qkd_server.infrastructure.keyManager.manager.BaseKeyEntityManager;
import com.xbg.qkd_server.infrastructure.keyManager.states.IManagerState;

import java.util.List;

/**
 * @author XBG
 * @description: 密钥管理器接口
 * @date 2025/1/1 13:14
 */
public interface KeyEntityManager<T extends IManagerState,F extends KeyForm> extends KeyAccessControl {

    /**
     * @description: 查询已经使用的密钥数量
     * @author: XBG
     * @date: 2025/1/1 13:41
     * @param:
     * @return:
     */
    T managerState();

    /**
     * @description: 通过KeyId获取已分配的密钥实体
     * @author: XBG
     * @date: 2025/1/1 14:08
     * @param:
     * @return:
     */
    KeyEntity findAssignedKeyByKeyId(String keyId);

    /**
     * @description: 通过saeId获取已分配的key
     * @author: XBG
     * @date: 2025/1/1 14:09
     * @param:
     * @return:
     */
    KeyEntity findAssignedKeyBySaeId(String saeId);

    /**
     * @description: 重新加载
     * @author: XBG
     * @date: 2025/1/1 14:12
     * @param:
     * @return: 手动刷新密钥
     */
    Integer freshKey();

    /**
     * @description: 批量获取密钥
     * @author: XBG
     * @date: 2025/1/1 14:16
     * @param:
     * @return:
     */
    List<KeyEntity> batchAcquireKeys(F form);

    /**
     * @description: 分配密钥
     * @author: XBG
     * @date: 2025/1/1 14:17
     * @param:
     * @return:
     */
    KeyEntity acquireKey(F form);
}
