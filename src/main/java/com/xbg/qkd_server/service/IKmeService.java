package com.xbg.qkd_server.service;

import com.xbg.qkd_server.common.dto.resp.StatusResp;
import com.xbg.qkd_server.common.dto.server.HandleResult;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.states.IManagerState;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  密钥服务接口
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 21:52
 */
public interface IKmeService {
    /**
     * 简单密钥获取接口，获取完全公开的密钥
     *
     * @param saeId master sae id
     * @param count 获取数量
     * @param size  获取长度
     * @return
     */
    HandleResult<List<KeyEntity>> acquireSimpleKey(String saeId, Integer count, Integer size);

    /**
     * 请求带有白名单机制的密钥
     *
     * @param saeId     master sae id
     * @param count     密钥数量
     * @param size      密钥长度
     * @param whiteList 白名单，白名单可以为空，空白名单表示不开启白名单管控
     * @return
     */
    HandleResult<List<KeyEntity>> acquireWhiteListControlKey(String saeId, Integer count, Integer size, List<String> whiteList);

    /**
     * 密钥查询接口
     * @param querySaeId
     * @param keyId
     * @return
     */
    HandleResult<List<KeyEntity>> querySAEKeyById(String querySaeId, List<String> keyId);

    /**
     * 功能描述:
     * <pre style="color:#fa5d19">
     *     状态获取
     * </pre>
     * @return:
     * @throws:
     * @author: XBG
     * @date: 2025/1/29 21:54
     */
    HandleResult<StatusResp> getKmeState(String slaveSAEId);

    /**
     * 功能描述:
     * <pre style="color:#fa5d19">
     *      包含密钥
     * </pre>
     *
     * @return:
     * @throws:
     * @author: XBG
     * @date: 2025/1/29 21:54
     */
    Boolean containTargetSAEKey(String targetSAE);
}
