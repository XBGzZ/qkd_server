package com.xbg.qkd_server.controller;

import com.xbg.qkd_server.adapter.ReturnData;
import com.xbg.qkd_server.common.dto.resp.StatusResp;
import com.xbg.qkd_server.common.dto.req.KeyAcquireReq;
import com.xbg.qkd_server.config.ApiConfig;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @description: sae服务提供
 * @author: XBG
 * @date: 2025/1/4 14:15
 */
@RestController
@RequestMapping(path = ApiConfig.PATH)
public class SAEController {
    /**
     * 节点状态获取，由Master SAE发起，查询任何一个Slave SAE
     * 发起者即 Master SAE ，被查询对象即Slave SAE
     * @param slaveSAEId
     * @return
     */
    @GetMapping("/{slave_SAE_ID}/status")
    public ReturnData GetKeyStatus(@PathVariable("slave_SAE_ID") String slaveSAEId) {
        System.out.printf("Sae id is %s \n", slaveSAEId);
        return new StatusResp();
    }

    /**
     * Post请求下的密钥获取，可配不配置参数，也可配置参数，支持扩展参数，扩展参数需要自定义
     * @param slaveSAEId
     * @return
     */
    @PostMapping("/{slave_SAE_ID}/enc_keys")
    public ReturnData PostKeyEncKeys(@PathVariable("slave_SAE_ID") String slaveSAEId) {
        System.out.printf("Sae id is %s \n", slaveSAEId);
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
    public ReturnData GetKeyEncKeys(@PathVariable("slave_SAE_ID") String slaveSAEId,
                                    @RequestParam(value = "number", required = false) Integer number,
                                    @RequestParam(value = "size", required = false) Integer size) {

        return KeyAcquireReq.builder().size(123).number(123).additionalSlaveSaeIds(List.of("alice", "bob")).build();
    }


    @PostMapping("/{master_SAE_ID}/dec_keys")
    public ReturnData PostKeyWithKeyIds(@PathVariable("master_SAE_ID") String masterSAEId) {
        System.out.printf("Sae id is %s \n", masterSAEId);
        return null;
    }
}
