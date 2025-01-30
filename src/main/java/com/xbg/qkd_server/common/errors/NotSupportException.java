package com.xbg.qkd_server.common.errors;

import com.xbg.qkd_server.common.enums.CommonErrorCode;

/**
 * @Author XBG
 * @Description:
 * @Date 2025-01-18
 */

public class NotSupportException extends KMEException {

    public NotSupportException() {
        super(CommonErrorCode.NOT_SUPPORT);
    }
}
