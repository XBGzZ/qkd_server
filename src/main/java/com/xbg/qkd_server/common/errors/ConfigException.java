package com.xbg.qkd_server.common.errors;

import com.xbg.qkd_server.common.enums.ErrorCode;

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
public class ConfigException extends KMEException{
    public ConfigException(ErrorCode errorCode) {
        super(errorCode);
    }
}
