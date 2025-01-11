package com.xbg.qkd_server.common.enums;

import lombok.Getter;

/**
 * @Author XBG
 * @Description: 密钥领域错误码
 * @Date 2025-01-11
 */
@Getter
public enum KmeServiceCode implements ErrorCode {

    KEY_ERROR("通用错误"),
    KEY_SAE_NO_PERMISSION_ACQUIRE_THE_KEY("没有密钥获取权限"),
    KEY_QUERY_SIZE_TOO_LONG("获取的密钥长度超过限制"),
    KEY_QUERY_SIZE_TOO_SHORT("获取的密钥长度过短"),
    KEY_QUERY_COUNT_OVER_LIMIT("密钥请求长度超过限制");

    private final String errorMsg;

    private KmeServiceCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String GetErrorMsg() {
        return errorMsg;
    }
}
