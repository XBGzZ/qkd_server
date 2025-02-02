package com.xbg.qkd_server.common.enums;

/**
 * @Author XBG
 * @Description:
 * @Date 2025-01-31
 */

public enum RouterErrorCode implements ErrorCode{
    ERROR_ROUTER_CONFIG("路由配置错误"),
    INVALID_IP_ADDRESS_FORMAT("错误Ip配置"),
    UNABLE_GET_SAE_IP_PORT("无法获取SAE的IP端口号")
    ;

    private final String errorMsg;

    private RouterErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}