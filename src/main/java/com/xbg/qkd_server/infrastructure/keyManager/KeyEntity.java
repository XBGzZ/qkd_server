package com.xbg.qkd_server.infrastructure.keyManager;

import java.util.Objects;

/**
 * @author XBG
 * @description: 密钥本体
 * @date 2025/1/1 13:14
 */
public interface KeyEntity extends KeyAccessControl {
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

    /**
     * @description: 设置所属者
     * @author: XBG
     * @date: 2025/1/11 19:25
     * @param:
     * @return:
     */
    Boolean setOwner(String owner);

    /**
     * @description: 所属者获取
     * @author: XBG
     * @date: 2025/1/13 1:32
     * @param:
     * @return:
     */
    String getOwner();

    /**
     * @description:
     * @author: XBG
     * @date: 2025/1/13 1:46
     * @param:
     * @return:
     */
    Long getAllocateTime();

    /**
     * 功能描述:
     * <pre style="color:#fa5d19">
     *     返回密钥长度
     * </pre>
     *
     * @return:
     * @throws:
     * @author: XBG
     * @date: 2025/1/29 1:15
     */
    Integer getKeySize();

    /**
     * 扩展字段
     *
     * @return
     */
    default Object getExtension() {
        return null;
    }
}
