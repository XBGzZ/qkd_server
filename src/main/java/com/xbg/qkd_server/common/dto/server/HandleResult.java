package com.xbg.qkd_server.common.dto.server;

import com.xbg.qkd_server.common.enums.ErrorCode;
import lombok.Builder;

/**
 * @Author XBG
 * @Description: 处理结果容器
 * @Date 2025-01-11
 */
@Builder
public class HandleResult<T> {
    private T result;
    private ErrorCode errorCode;
}
