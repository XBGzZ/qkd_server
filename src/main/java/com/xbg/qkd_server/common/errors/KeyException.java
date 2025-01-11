package com.xbg.qkd_server.common.errors;

/**
 * @Author XBG
 * @Description: 密钥异常基类
 * @Date 2025-01-11
 */

public class KeyException extends KMEException {
    public KeyException(String errorInfo) {
        super(errorInfo);
    }
}
