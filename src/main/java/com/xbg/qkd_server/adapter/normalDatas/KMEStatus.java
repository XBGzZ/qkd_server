package com.xbg.qkd_server.adapter.normalDatas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xbg.qkd_server.adapter.NormalData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author XBG
 * @Description: 数据查询结果Pojo类
 * @Date 2024-12-01
 */

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KMEStatus extends NormalData {
    // 源端 KME id
    @JsonProperty("source_KME_ID")
    String sourceKMEId;

    // 宿端 KME id
    @JsonProperty("target_KME_ID")
    String targetKMEId;

    // 主 SAE id
    @JsonProperty("master_SAE_ID")
    String masterSAEId;

    // 指定 slave SAE id
    @JsonProperty("slave_SAE_ID")
    String slaveSAEId;

    // KME默认可分发密钥数量
    @JsonProperty("key_size")
    Integer keySize;

    // 当前节点默认可分发给SAE的密钥数量
    @JsonProperty("stored_key_count")
    Integer stroedKeyCount;

    // 最大短密钥数量
    @JsonProperty("max_key_count")
    Integer maxKeyCount;

    // 最大对端可请求数量
    @JsonProperty("max_key_per_request")
    Integer maxKeyPerRequest;

    // 最大可请求密钥长度
    @JsonProperty("max_key_size")
    Integer maxKeySize;

    // 最小可请求密钥长度
    @JsonProperty("min_key_size")
    Integer minKeySize;

    // 最大可支持的SAE Id
    @JsonProperty("max_SAE_ID_count")
    Integer maxSAEIdCount;

    @JsonProperty("status_extension")
    Object extension;
}

