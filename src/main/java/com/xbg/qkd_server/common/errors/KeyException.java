package com.xbg.qkd_server.common.errors;

import com.xbg.qkd_server.common.enums.ErrorCode;

/**
 * @Author XBG
 * @Description: 密钥异常基类
 * @Date 2025-01-11
 */

public class KeyException extends KMEException {
    public KeyException(ErrorCode errorInfo) {
        super(errorInfo);
    }
}
