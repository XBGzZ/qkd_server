package com.xbg.qkd_server.service.impl;

import com.xbg.qkd_server.common.dto.server.HandleResult;
import com.xbg.qkd_server.common.tools.MathTool;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.service.LocalKmeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 本地密钥管理服务类
 * 内部存在两个Map管理密钥，由于负载子是75%，为了避免长度过大
 * 会使用线程池进行回收动作
 * <p>
 * 必须配置：
 * 1、密钥生存时间（低负载下节省空间）
 * 2、密钥最大数量（控制密钥上限，预生产密钥+
 * <p>
 * 三种模式：
 * 1、实时生产模式
 * 2、预生产模式
 * 3、预生产+实时生产模式
 * 1、实时生产：请求的时候生产密钥，可有效节省空间，必须配置最大上限
 * 2、预生产：会按照预生产上限进行配置，
 * @author: XBG
 * @date: 2025/1/11 19:16
 * @param:
 * @return:
 */
@Service
public class LocalKmeServiceImpl implements LocalKmeService {

    @Override
    public HandleResult<List<KeyEntity>> AcquireSimpleKey(String saeId, Integer count, Integer size) {
        return null;
    }

    @Override
    public HandleResult<List<KeyEntity>> AcquireWhiteListControlKey(String saeId, Integer count, Integer size, List<String> whiteList) {
        return null;
    }

    @Override
    public HandleResult<KeyEntity> QuerySAEKeyById(String saeId, String keyId) {
        return null;
    }
}
