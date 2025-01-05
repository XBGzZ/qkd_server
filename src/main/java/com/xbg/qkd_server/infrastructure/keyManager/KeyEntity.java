package com.xbg.qkd_server.infrastructure.keyManager;

/**
 * @author XBG
 * @description: 密钥本体
 * @date 2025/1/1 13:14
 */
public interface KeyEntity {
    /**
     * @description: 密钥是否使用
     * @author: XBG
     * @date: 2025/1/1 13:31
     * @param:
     * @return:
     */
    Boolean isUsing();
    /**
     * @description: 获取Base64密钥
     * @author: XBG
     * @date: 2025/1/1 13:32
     * @param:
     * @return:
     */
    String getKey();
    /**
     * @description: 获取 KeyId
     * @author: XBG
     * @date: 2025/1/1 13:32
     * @param:
     * @return:
     */
    String getKeyId();

}
