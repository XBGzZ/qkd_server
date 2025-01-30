package com.xbg.qkd_server.infrastructure.keyManager.states;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xbg.qkd_server.common.errors.NotSupportException;
import lombok.Builder;

/**
 * @author XBG
 * @description:
 * @date 2025/1/14 22:41
 */
@Builder
public class ManagerState implements IManagerState<Object> {

    // 目标KEM默认密钥长度
    @JsonProperty("key_size")
    Integer keySize;

    // 目标KME可分配密钥数量
    @JsonProperty("stored_key_count")
    Integer storedKeyCount;

    // 目标KME最大密钥数量
    @JsonProperty("max_key_count")
    Integer maxKeyCount;

    // 目标KME单次最大可请求密钥数量
    @JsonProperty("max_key_per_request")
    Integer maxKeyPerRequest;

    // 目标KME最大密钥长度
    @JsonProperty("max_key_size")
    Integer maxKeySize;

    // 目标KME最小密钥长度
    @JsonProperty("min_key_size")
    Integer minKeySize;

    // 目标KME最大SAE支持数量
    @JsonProperty("max_SAE_ID_count")
    Integer maxSAEIdCount;

    @JsonProperty("target_KME_ID")
    String targetKMEId;

    @Override
    public Integer getDefaultKeySize() {
        return keySize;
    }

    @Override
    public Integer getMaxKeyCount() {
        return maxKeyCount;
    }

    @Override
    public Integer getStoredKeyCount() {
        return storedKeyCount;
    }

    @Override
    public Integer getMaxKeyPerRequest() {
        return maxKeyPerRequest;
    }

    @Override
    public Integer getMaxKeySize() {
        return maxKeySize;
    }

    @Override
    public Integer getMinKeySize() {
        return minKeySize;
    }

    @Override
    public Integer getMaxSAEIdCount() {
        return maxSAEIdCount;
    }

    /**
     * 不支持扩展字段
     * @return
     */
    @Override
    public Object getExtensionStatus() {
        throw new NotSupportException();
    }

    public String getTargetKMEId() {
        return targetKMEId;
    }
}
