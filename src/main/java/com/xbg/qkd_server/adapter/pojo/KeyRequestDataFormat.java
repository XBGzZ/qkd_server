package com.xbg.qkd_server.adapter.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author XBG
 * @Description: 密钥请求返回
 * @Date 2024-12-01
 */

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyRequestDataFormat {

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
