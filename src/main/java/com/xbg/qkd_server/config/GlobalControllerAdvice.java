package com.xbg.qkd_server.config;

import com.xbg.qkd_server.adapter.ErrorData;
import com.xbg.qkd_server.adapter.ExceptionData;
import com.xbg.qkd_server.adapter.ReturnData;
import com.xbg.qkd_server.common.errors.KMEException;
import jakarta.annotation.Priority;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalTime;

/**
 * @Author XBG
 * @Description: 异常拦截配置
 * @Date 2024-12-01
 */

@ControllerAdvice
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

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ReturnData NotFoundException(NoHandlerFoundException e){
        return ExceptionData.builder().timestamp(LocalTime.now())
                .status(e.getStatusCode().value())
                .error(HttpStatus.valueOf(e.getStatusCode().value()).getReasonPhrase())
                .message(e.getMessage())
                .path(e.getRequestURL())
                .build();
    }
}
