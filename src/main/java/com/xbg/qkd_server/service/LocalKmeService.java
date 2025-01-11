package com.xbg.qkd_server.service;

import com.xbg.qkd_server.common.dto.server.HandleResult;
import com.xbg.qkd_server.common.errors.KeyException;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;

import java.util.List;
import java.util.Optional;

/**
 * @description: 本地SAE服务接口
 * @author: XBG
 * @date: 2025/1/4 14:15
 */
public interface LocalKmeService {
    /**
     * 简单密钥获取接口，获取完全公开的密钥
     *
     * @param saeId master sae id
     * @param count 获取数量
     * @param size  获取长度
     * @return
     * @throws KeyException
     */
    HandleResult<List<KeyEntity>> AcquireSimpleKey(String saeId, Integer count, Integer size);

    /**
     * 请求带有白名单机制的密钥
     * @param saeId master sae id
     * @param count 密钥数量
     * @param size 密钥长度
     * @param whiteList 白名单，白名单可以为空，空白名单表示不开启白名单管控
     * @return
     */
    HandleResult<List<KeyEntity>> AcquireWhiteListControlKey(String saeId, Integer count, Integer size, List<String> whiteList);

    /**
     * 密钥查询接口
     *
     * @param saeId
     * @param keyId
     * @return
     * @throws KeyException
     */
    HandleResult<KeyEntity> QuerySAEKeyById(String saeId, String keyId);
}
