package com.xbg.qkd_server.common.enums;

/**
 * @Author XBG
 * @Description: 错误码接口
 * @Date 2025-01-11
 */

public interface ErrorCode {
    /**
     * 获取错误信息
     * @return
     */
    String GetErrorMsg();

    /**
     * 判断是否成功
     * @return
     */
    default Boolean IsSuccess() {
        return this instanceof GenerateCode;
    }
}
