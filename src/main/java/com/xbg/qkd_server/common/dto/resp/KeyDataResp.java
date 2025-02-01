package com.xbg.qkd_server.common.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xbg.qkd_server.adapter.NormalData;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author XBG
 * @Description: 密钥响应DTO
 * @Date 2024-12-01
 */

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
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
    @Builder
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

        public static KeyDataInfo adapter(KeyEntity keyEntity) {
            return KeyDataInfo.builder()
                    .key(keyEntity.getKey())
                    .keyId(keyEntity.getKeyId())
                    .keyExtension(keyEntity.getExtension())
                    .build();
        }
    }

}
