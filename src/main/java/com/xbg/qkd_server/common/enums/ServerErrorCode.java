package com.xbg.qkd_server.common.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 19:53
 */
public enum ServerErrorCode implements ErrorCode {
    // =============== 密钥相关通用异常 ================
    KEY_SERVER_ERROR("密钥服务层通用错误"),
    KEY_SERVER_INVALID_PARAM("参数不合法"),
    ;

    private final String errorMsg;

    private ServerErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
