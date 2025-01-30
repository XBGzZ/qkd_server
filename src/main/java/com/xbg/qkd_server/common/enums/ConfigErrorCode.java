package com.xbg.qkd_server.common.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-28 21:05
 */
public enum ConfigErrorCode implements ErrorCode{
    ANNOTATION_MISSING_FILED("注释缺少配置字段"),
    MISSING_ANNOTATION("缺少注释"),
    MISSING_CONFIG("缺少配置项"),

    ;

    private final String errorMsg;

    private ConfigErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
