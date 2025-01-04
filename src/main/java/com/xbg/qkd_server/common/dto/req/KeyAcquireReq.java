package com.xbg.qkd_server.common.dto.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.xbg.qkd_server.adapter.NormalData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author XBG
 * @Description: 密钥分配请求
 * @Date 2024-12-01
 */

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyAcquireReq extends NormalData {

    // 密钥请求数量
    @JsonProperty("number")
    Integer number;

    // 密钥长度
    @JsonProperty("size")
    Integer size;

    // 指定可共享此密钥的slave SAE，默认情况全局可见
    @JsonProperty("additional_slave_SAE_IDs")
    List<String> additionalSlaveSaeIds;

    // 扩展字段
    @JsonProperty("extension_mandatory")
    List<JsonNode> extensionMandatory;

    // 扩展选项
    @JsonProperty("extension_optional")
    List<JsonNode> extensionOptional;
}
