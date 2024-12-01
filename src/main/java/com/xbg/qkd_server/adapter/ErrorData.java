package com.xbg.qkd_server.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author XBG
 * @Description: 错误数据类型
 * @Date 2024-12-01
 */

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData implements  ReturnData{
    @JsonProperty("message")
    String message;

    @JsonProperty("details")
    List<Object> details;

    public void AddDetails(Object detail) {
        details.add(detail);
    }
}
