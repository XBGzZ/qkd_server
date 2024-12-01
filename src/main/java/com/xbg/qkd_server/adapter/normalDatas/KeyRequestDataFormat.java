package com.xbg.qkd_server.adapter.normalDatas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xbg.qkd_server.adapter.NormalData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author XBG
 * @Description: 密钥请求返回
 * @Date 2024-12-01
 */

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyRequestDataFormat extends NormalData {

    @JsonProperty("number")
    Integer number;

    @JsonProperty("size")
    Integer size;

    @JsonProperty("additional_slave_SAE_IDs")
    List<String> additionalSlaveSaeIds;

    @JsonProperty("extension_mandatory")
    List<Object> extensionMandatory;

    @JsonProperty("extension_optional")
    List<Object> extensionOptional;
}
