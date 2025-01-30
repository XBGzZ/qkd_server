package com.xbg.qkd_server.common.errors;

import com.xbg.qkd_server.common.enums.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @Author XBG
 * @Description: 内部异常
 * @Date 2024-12-01
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class KMEException extends RuntimeException {
    ErrorCode errorCode;
    String detail;
    public KMEException(ErrorCode errorCode,String detail) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public KMEException(ErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode;
        detail = errorCode.getErrorMsg();
    }

}
