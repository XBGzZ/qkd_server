package com.xbg.qkd_server.infrastructure.keyManager;

import java.util.List;

/**
 * @author XBG
 * @description: 密钥管理器接口
 * @date 2025/1/1 13:14
 */
public interface KeyManager {

    /**
     * @description: 查询已经使用的密钥数量
     * @author: XBG
     * @date: 2025/1/1 13:41
     * @param:
     * @return:
     */
    Integer AssignedKeysCount();

    /**
     * @description: 查询没有使用的密钥数量
     * @author: XBG
     * @date: 2025/1/1 14:08
     * @param:
     * @return:
     */
    Integer UndistributedKeysCount();

    /**
     * @description: 通过KeyId获取已分配的密钥实体
     * @author: XBG
     * @date: 2025/1/1 14:08
     * @param:
     * @return:
     */
    KeyEntity FindAssignedKeyByKeyId(String keyId);

    /**
     * @description: 通过saeId获取已分配的key
     * @author: XBG
     * @date: 2025/1/1 14:09
     * @param:
     * @return:
     */
    KeyEntity FindAssignedKeyBySaeId(String saeId);

    /**
     * @description: 获取配置文件
     * @author: XBG
     * @date: 2025/1/1 14:11
     * @param:
     * @return:
     */
    KeyManagerConfig GetKeyManagerConfig();

    /**
     * @description: 重新加载
     * @author: XBG
     * @date: 2025/1/1 14:12
     * @param:
     * @return: 加载的数量
     */
    Integer ReloadKey();

    /**
     * @description: 批量获取密钥
     * @author: XBG
     * @date: 2025/1/1 14:16
     * @param:
     * @return:
     */
    List<KeyEntity> BatchAcquireKeys(String saeId, Integer count);

    /**
     * @description: 分配密钥
     * @author: XBG
     * @date: 2025/1/1 14:17
     * @param:
     * @return:
     */
    KeyEntity AcquireKey(String saeId);

}
