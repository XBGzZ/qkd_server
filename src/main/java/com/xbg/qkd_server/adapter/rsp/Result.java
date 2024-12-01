package com.xbg.qkd_server.adapter.rsp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author XBG
 * @Description: 统一的返回容器
 * @Date 2024-12-01
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    @JsonProperty("message")
    String message;

    @JsonProperty("details")
    List<Object> details;

    public void AddDetails(Object detail) {
        details.add(detail);
    }
}
