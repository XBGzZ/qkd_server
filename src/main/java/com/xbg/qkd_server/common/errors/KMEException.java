package com.xbg.qkd_server.common.errors;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author XBG
 * @Description: 内部异常
 * @Date 2024-12-01
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class KMEException extends Exception {
    String errorInfo;
    Integer errorCode;
    public KMEException(String errorInfo) {
        super(errorInfo);
        this.errorInfo = errorInfo;
    }
}
