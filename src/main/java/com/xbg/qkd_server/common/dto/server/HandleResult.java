package com.xbg.qkd_server.common.dto.server;

import com.xbg.qkd_server.common.enums.CommonCode;
import com.xbg.qkd_server.common.enums.ErrorCode;
import lombok.Data;
import lombok.Getter;

/**
 * @Author XBG
 * @Description: 处理结果容器
 * @Date 2025-01-11
 */
@Data
public class HandleResult<T> {
    private T result;
    private ErrorCode errorCode = CommonCode.SUCCESS;

    public Boolean isSuccess() {
        return errorCode == CommonCode.SUCCESS;
    }
    public static <T> HandleResultBuilder<T> builder() {
        return new HandleResultBuilder<T>(new HandleResult<>());
    }

    public static class HandleResultBuilder<BT> {
        private HandleResult<BT> buildTarget;

        private HandleResultBuilder(HandleResult<BT> result) {
            this.buildTarget = result;
        }

        public HandleResultBuilder<BT> result(BT result) {
            buildTarget.result = result;
            return this;
        }

        public HandleResultBuilder<BT> errorCode(ErrorCode errorCode) {
            buildTarget.errorCode = errorCode;
            return this;
        }

        public HandleResult<BT> build() {
            try {
                return buildTarget;
            } finally {
                buildTarget = null;
            }
        }
    }

}
