package com.xbg.qkd_server.service.impl;

import com.xbg.qkd_server.common.dto.resp.KeyDataResp;
import com.xbg.qkd_server.common.dto.resp.StatusResp;
import com.xbg.qkd_server.common.dto.server.HandleResult;
import com.xbg.qkd_server.common.enums.CommonErrorCode;
import com.xbg.qkd_server.common.enums.ErrorCode;
import com.xbg.qkd_server.common.enums.KeyErrorCode;
import com.xbg.qkd_server.common.enums.ServerErrorCode;
import com.xbg.qkd_server.common.errors.NotSupportException;
import com.xbg.qkd_server.common.tools.MathTool;
import com.xbg.qkd_server.infrastructure.RouterManager.KmeRouterManager;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityManager;
import com.xbg.qkd_server.infrastructure.keyManager.states.IManagerState;
import com.xbg.qkd_server.service.LocalKmeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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
@Slf4j
public class LocalKmeServiceImpl implements LocalKmeService {
    // TODO 不知道什么地方循环依赖了
    // 导致必须加@Lazy，本身是没有进行循环依赖的
    @Lazy
    @Autowired
    KeyEntityManager manager;

    @Autowired
    KmeRouterManager routerManager;

    @Override
    public HandleResult<KeyDataResp> acquireSimpleKey(String saeId, Integer count, Integer size) {
        if (!acquireSimpleCheck(saeId, count, size)) {
            return HandleResult.<KeyDataResp>builder()
                    .errorCode(KeyErrorCode.KEY_ERROR)
                    .build();
        }
        List<KeyEntity> keyEntities = manager.acquireKeys(saeId, count, size);
        KeyDataResp keyDataResp = translateToKeyDataResp(keyEntities);
        return HandleResult.<KeyDataResp>builder()
                .result(keyDataResp)
                .build();
    }

    @Override
    public HandleResult<KeyDataResp> acquireWhiteListControlKey(String saeId, Integer count, Integer size, List<String> whiteList) {
        if (Objects.isNull(whiteList) || whiteList.isEmpty()) {
            return acquireSimpleKey(saeId, count, size);
        }
        List<KeyEntity> keyEntities = manager.acquireKeys(saeId, count, size);
        keyEntities.forEach(item -> {
            item.loadWhiteList(whiteList);
        });
        KeyDataResp keyDataResp = translateToKeyDataResp(keyEntities);
        return HandleResult.<KeyDataResp>builder()
                .result(keyDataResp)
                .build();
    }

    private KeyDataResp translateToKeyDataResp(List<KeyEntity> keyEntities) {
        List<KeyDataResp.KeyDataInfo> list = keyEntities.stream()
                .map(KeyDataResp.KeyDataInfo::adapter)
                .toList();
        return KeyDataResp.builder()
                .keys(list)
                .build();
    }

    @Override
    public HandleResult<KeyDataResp> querySAEKeyById(String querySaeId, List<String> keyId) {
        List<KeyEntity> keyEntities = manager.queryAssignedKeyByKeyId(keyId);
        if (keyEntities.isEmpty()) {
            log.warn("All [{}] keyIds is not exists",keyId.size());
            return HandleResult.<KeyDataResp>builder()
                    .errorCode(KeyErrorCode.KEY_ERROR)
                    .build();
        }
        ArrayList<KeyEntity> result = new ArrayList<>();
        for (var item : keyEntities) {
            if (!item.isAccessAble(querySaeId)) {
                log.warn("SAE[{}] can't access Key [{}]", querySaeId, item.getKeyId());
                continue;
            }
            result.add(item);
        }
        KeyDataResp keyDataResp = translateToKeyDataResp(result);
        return HandleResult.<KeyDataResp>builder()
                .result(keyDataResp)
                .build();
    }

    /**
     * 同KME不同SAE互相查询
     * 如果查询的SAE和被查询的SAE是同一个KME的
     * 那么返回的target和source KME是同一个KME
     *
     * @param slaveSAEId
     * @return
     */
    @Override
    public HandleResult<StatusResp> getKmeState(String slaveSAEId) {
        IManagerState<?> iManagerState = manager.managerState();
        if (Objects.isNull(iManagerState)) {
            return HandleResult.<StatusResp>builder()
                    .errorCode(CommonErrorCode.NULL_POINTER)
                    .build();
        }
        StatusResp adapter = StatusResp.adapter(iManagerState);
        // 只需要填写对应的信息即可
        adapter.setTarget(slaveSAEId, routerManager.getCurrentKME().nodeId());
        adapter.setSource(routerManager.getCurrentSAEId().nodeId(), routerManager.getCurrentKME().nodeId());
        return HandleResult.<StatusResp>builder()
                .result(adapter)
                .build();
    }

    @Override
    public Boolean containTargetSAEKey(String targetSAE) {
        return manager.containTargetSAEKey(targetSAE);
    }

    /**
     * 密钥请求简单校验，目的是初步过滤
     * 过滤细节，应该由工厂负责，判断是否
     * 能够完成Key生产
     *
     * @param saeId
     * @param count
     * @param size
     * @return
     */
    private Boolean acquireSimpleCheck(String saeId, Integer count, Integer size) {
        return StringUtils.hasLength(saeId) && count > 0 && size > 0;
    }
}
