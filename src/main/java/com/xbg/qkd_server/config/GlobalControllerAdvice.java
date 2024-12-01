package com.xbg.qkd_server.config;

import com.xbg.qkd_server.adapter.ErrorData;
import com.xbg.qkd_server.adapter.ReturnData;
import com.xbg.qkd_server.common.errors.KMEException;
import jakarta.annotation.Priority;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author XBG
 * @Description: 异常拦截
 * @Date 2024-12-01
 */

@ControllerAdvice(basePackages = "com.xbg.qkd_server")
@Priority(1)
public class GlobalControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReturnData globalExceptionHandler(Exception e){
        return ErrorData.builder().message(e.getMessage()).build();
    }

    @ExceptionHandler(KMEException.class)
    @ResponseBody
    public ReturnData kmeExceptionHandler(KMEException e){
        return ErrorData.builder().message(e.getErrorInfo()).build();
    }
}
