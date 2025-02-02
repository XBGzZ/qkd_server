package com.xbg.qkd_server.common.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author XBG
 * @Description: 密钥请求
 * @Date 2025-01-04
 */
@Data
public class KeyQueryReq {
    @JsonProperty("key_IDs")
    private List<KeyQueryInfo> keyQueryReqInfo;

    @Data
    public static class KeyQueryInfo {
        @JsonProperty("key_ID")
        private String keyId;

        // 扩展字段
        @JsonProperty("key_ID_extension")
        private String keyIdExtension;
    }
}
