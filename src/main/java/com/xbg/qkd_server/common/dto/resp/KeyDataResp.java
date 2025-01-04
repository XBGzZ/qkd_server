package com.xbg.qkd_server.common.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xbg.qkd_server.adapter.NormalData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author XBG
 * @Description: 密钥响应DTO
 * @Date 2024-12-01
 */

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyDataResp extends NormalData {

    @JsonProperty("Keys")
    List<KeyDataInfo> keys;

    // 密钥容器扩展字段
    @JsonProperty("key_container_extension")
    Object keyContainerExtension;

    /**
     * @description: 详细信息
     * @author: XBG
     * @date: 2025/1/4 15:20
     */
    @Data
    @EqualsAndHashCode(callSuper = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class KeyDataInfo extends NormalData {
        // UUID 的密钥索引
        @JsonProperty("key_ID")
        String keyId;

        @JsonProperty("key_ID_extension")
        Object keyIdExtension;

        // base64编码的密钥
        @JsonProperty("key")
        String key;

        // 密钥扩展字段
        @JsonProperty("key_extension")
        Object keyExtension;

    }

}
