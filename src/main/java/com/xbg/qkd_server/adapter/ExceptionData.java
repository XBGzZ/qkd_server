package com.xbg.qkd_server.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

/**
 * @Author XBG
 * @Description: 服务器异常
 * @Date 2025-01-04
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionData implements ReturnData {
    @JsonProperty("timestamp")
    LocalTime timestamp;

    @JsonProperty("status")
    Integer status;

    @JsonProperty("error")
    String error;

    @JsonProperty("message")
    String message;

    @JsonProperty("path")
    String path;
}
