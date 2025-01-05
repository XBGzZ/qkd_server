package com.xbg.qkd_server.infrastructure.keyManager.impl;
/**
 * @Author XBG
 * @Description: 密钥访问权限增强接口
 * @Date 2025-01-05
 */

public interface KeyAccessControl {
    /**
     * @description: 访问权限判断
     * @author: XBG
     * @date: 2025/1/5 12:56
     * @param:
     * @return:
     */
    Boolean isAccessAble(String saeId);
    /**
     * @description: 设置使用的saeI
     * @author: XBG
     * @date: 2025/1/1 13:32
     * @param:
     * @return:
     */
    Boolean setOwner(String saeId);

    /**
     * @description: 获取对象
     * @author: XBG
     * @date: 2025/1/1 22:27
     * @param:
     * @return:
     */
    String getOwner();
}
