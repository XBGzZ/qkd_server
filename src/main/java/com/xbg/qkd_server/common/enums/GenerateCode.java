package com.xbg.qkd_server.common.enums;

/**
 * @Author XBG
 * @Description: 通用成功码
 * @Date 2025-01-11
 */

public enum GenerateCode implements ErrorCode {
    SUCCESS("服务处理成功");
    private final String errorMsg;

    private GenerateCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String GetErrorMsg() {
        return errorMsg;
    }
}
