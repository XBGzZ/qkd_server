package com.xbg.qkd_server.common.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xbg.qkd_server.adapter.NormalData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author XBG
 * @Description: SAE查询目标SAE结果
 * @Date 2024-12-01
 */

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StatusResp extends NormalData {
    // 当前直连KME ID
    @JsonProperty("source_KME_ID")
    String sourceKMEId;

    // 目标SAE所属KME ID
    @JsonProperty("target_KME_ID")
    String targetKMEId;

    // 当前SAE id
    @JsonProperty("master_SAE_ID")
    String masterSAEId;

    // 所请求对端的 SAE id
    @JsonProperty("slave_SAE_ID")
    String slaveSAEId;

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

    // 扩展字段
    @JsonProperty("status_extension")
    Object extension;
}

