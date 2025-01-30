package com.xbg.qkd_server.infrastructure.keyManager;

import java.util.List;

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
    default Boolean isAccessAble(String saeId) {
        return true;
    }

    default Boolean loadWhiteList(List<String> saeIds) {
        return true;
    }

}
