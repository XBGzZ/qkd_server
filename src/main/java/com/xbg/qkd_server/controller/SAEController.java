package com.xbg.qkd_server.controller;

import com.xbg.qkd_server.adapter.ReturnData;
import com.xbg.qkd_server.common.dto.resp.StatusResp;
import com.xbg.qkd_server.common.dto.req.KeyAcquireReq;
import com.xbg.qkd_server.common.dto.server.HandleResult;
import com.xbg.qkd_server.common.errors.KMEException;
import com.xbg.qkd_server.common.errors.KeyException;
import com.xbg.qkd_server.config.ApiConfig;
import com.xbg.qkd_server.infrastructure.keyManager.states.IManagerState;
import com.xbg.qkd_server.service.IKmeService;
import com.xbg.qkd_server.service.IRouterService;
import com.xbg.qkd_server.service.LocalKmeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @description: sae服务提供
 * @author: XBG
 * @date: 2025/1/4 14:15
 */
@RestController
@RequestMapping(path = ApiConfig.PATH)
@Slf4j
public class SAEController {

    @Autowired
    LocalKmeService localKmeService;

    @Autowired
    IRouterService routerService;

    /**
     * 节点状态获取，由Master SAE发起，查询任何一个Slave SAE
     * 发起者即 Master SAE ，被查询对象即Slave SAE
     * @param slaveSAEId
     * @return
     */
    @GetMapping("/{slave_SAE_ID}/status")
    public ReturnData getKeyStatus(@PathVariable("slave_SAE_ID") @NonNull String slaveSAEId) {
        // 按照路由配置寻找对应KME服务
        IKmeService iKmeService = kmeRoute(slaveSAEId);
        if (Objects.isNull(iKmeService)) {
            // 先返回空
            return KeyAcquireReq.builder().build();
        }
        HandleResult<StatusResp> kmeState = iKmeService.getKmeState(slaveSAEId);
        if (!kmeState.isSuccess()) {
            log.error("query [{}] status failed",slaveSAEId);
            throw new KeyException(kmeState.getErrorCode());
        }
        return kmeState.getResult();
    }

    /**
     * Post请求下的密钥获取，可配不配置参数，也可配置参数，支持扩展参数，扩展参数需要自定义
     * @param slaveSAEId
     * @return
     */
    @PostMapping("/{slave_SAE_ID}/enc_keys")
    public ReturnData postKeyEncKeys(@PathVariable("slave_SAE_ID") @NonNull String slaveSAEId, KeyAcquireReq keyAcquire) {
//        if(keyAcquire)

        return KeyAcquireReq.builder().size(2123).number(123).additionalSlaveSaeIds(List.of("alice", "bob")).build();
    }

    /**
     * Get请求下的密钥，仅存在number和size时可用
     * @param slaveSAEId
     * @param number 请求密钥数量
     * @param size 单个密钥长度
     * @return
     */
    @GetMapping("/{slave_SAE_ID}/enc_keys")
    public ReturnData getKeyEncKeys(@PathVariable("slave_SAE_ID") String slaveSAEId,
                                    @RequestParam(value = "number", required = false) Integer number,
                                    @RequestParam(value = "size", required = false) Integer size) {

        return KeyAcquireReq.builder().size(123).number(123).additionalSlaveSaeIds(List.of("alice", "bob")).build();
    }


    @PostMapping("/{master_SAE_ID}/dec_keys")
    public ReturnData postKeyWithKeyIds(@PathVariable("master_SAE_ID") String masterSAEId) {
        System.out.printf("Sae id is %s \n", masterSAEId);
        return null;
    }

    protected IKmeService kmeRoute(String targetSAEId) {
        Boolean contain = localKmeService.containTargetSAEKey(targetSAEId);
        if(contain) {
            return localKmeService;
        } else {
            // TODO 跨端服务
            return null;
        }
    }
}
