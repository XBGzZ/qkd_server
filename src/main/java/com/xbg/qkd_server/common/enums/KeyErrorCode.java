package com.xbg.qkd_server.common.enums;

/**
 * @Author XBG
 * @Description:
 * @Date 2025-01-18
 */

public enum KeyErrorCode implements ErrorCode {

    OVER_COUNT_LIMIT("密钥数量超过限制"),
    TRY_LOCK_OVER_TIME("上锁超时"),
    TRY_LOCK_EXCEPTION("上锁异常"),
    ;

    private final String errorMsg;

    private KeyErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String GetErrorMsg() {
        return errorMsg;
    }

}
