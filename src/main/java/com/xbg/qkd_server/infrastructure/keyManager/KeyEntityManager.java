package com.xbg.qkd_server.infrastructure.keyManager;

import com.xbg.qkd_server.infrastructure.keyManager.states.IManagerState;

import java.util.List;
import java.util.Set;

/**
 * @author XBG
 * @description: 密钥管理器接口
 * @date 2025/1/1 13:14
 */
public interface KeyEntityManager<T extends IManagerState> extends KeyAccessControl {

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
    Set<KeyEntity> queryAssignedKeyByKeyId(Set<String> keyIds);

    /**
     * @description: 通过saeId获取已分配的key
     * @author: XBG
     * @date: 2025/1/1 14:09
     * @param:
     * @return:
     */
    List<KeyEntity> findAssignedKeyBySaeId(String saeId);

    /**
     * @description: 申请密钥
     * @author: XBG
     * @date: 2025/1/1 14:16
     * @param:
     * @return:
     */
    List<KeyEntity> acquireKeys(String owner, Integer count, Integer size);

}
