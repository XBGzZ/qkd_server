package com.xbg.qkd_server.infrastructure.keyManager.keyEntity;

import com.xbg.qkd_server.infrastructure.keyManager.KeyAccessControl;

import java.util.Set;

/**
 * @Author XBG
 * @Description: 白名单能力增强接口
 * @Date 2025-01-05
 */

public interface WhiteListAbility extends KeyAccessControl {
    /**
     * @description: 添加可访问SAE
     * @author: XBG
     * @date: 2025/1/5 12:59
     * @param:
     * @return:
     */
    Boolean addWhiteListSaeIds(Set<String> saeIds);

    /**
     * @description: 删除可访问的SAE
     * @author: XBG
     * @date: 2025/1/5 13:00
     * @param:
     * @return:
     */
    Boolean removeWhiteListSaeIds(Set<String> saeIds);

    /**
     * @description: 访问控制开关
     * @author: XBG
     * @date: 2025/1/5 13:10
     * @param:
     * @return:
     */
    Boolean enableWhiteList();

    /**
     * @description: 关闭访问控制开关
     * @author: XBG
     * @date: 2025/1/5 13:11
     * @param:
     * @return:
     */
    Boolean disableWhiteList();

    /**
     * @description: 开关状态查询
     * @author: XBG
     * @date: 2025/1/5 13:12
     * @param:
     * @return:
     */
    Boolean isWhiteListEnabled();
}
