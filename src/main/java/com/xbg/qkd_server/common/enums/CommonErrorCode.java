package com.xbg.qkd_server.common.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 16:34
 */
public enum CommonErrorCode implements ErrorCode{

    NOT_SUPPORT("功能不支持"),
    NULL_POINTER("空指针");
    private final String errorMsg;

    private CommonErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
