package com.xbg.qkd_server.common.enums;

/**
 * @Author XBG
 * @Description:
 * @Date 2025-01-31
 */

public enum RouterErrorCode implements ErrorCode{
    ERROR_ROUTER_CONFIG("路由配置错误"),
    INVALID_IP_ADDRESS_FORMAT("错误Ip配置"),
    UNABLE_GET_SAE_IP_PORT("无法获取SAE的IP端口号"),
    CAN_NOT_FIND_SAE_NODE("无法找到对应的SAE节点信息"),
    MISSING_CURRENT_KME_ID("缺少当前KME配置信息"),
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