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
public enum CertErrorCode implements ErrorCode{

    EMPTY_CERT("无法获取证书"),
    ;
    private final String errorMsg;

    private CertErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
