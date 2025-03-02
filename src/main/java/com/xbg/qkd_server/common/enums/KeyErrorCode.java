package com.xbg.qkd_server.common.enums;

/**
 * @Author XBG
 * @Description:
 * @Date 2025-01-18
 */

public enum KeyErrorCode implements ErrorCode {
    // =============== 配置相关 ================
    CONFIG_ERROR_INVALID_INIT_PARAM("不合法的初始化参数"),
    // =============== 密钥相关通用异常 ================
    KEY_ERROR("通用错误"),
    KEY_TRY_LOCK_OVER_TIME("上锁超时"),
    KEY_TRY_LOCK_EXCEPTION("上锁异常"),
    KEY_SIZE_CONFIG_INVALID("密钥配置项异常"),
    KEY_DUPLICATE_KEY_ENTITY_ID("重复的密钥ID"),

    // =============== 密钥请求合法性相关 ================
    KEY_SAE_ID_IS_INVALID("申请的SAE ID不合法"),
    KEY_PRE_ALLOCATE_FAILED("预分配密钥失败，需要清理密钥"),
    KEY_OVER_PER_REQUEST_LIMIT("请求密钥数量超过单次请求上限"),

    KEY_COUNT_IS_NO_MORE_THAN_ZERO("申请的密钥数量过少"),
    KEY_SIZE_INVALID("申请的密钥长度异常，需要是8的整数倍"),
    KEY_SIZE_TOO_LONG("获取的密钥长度超过限制"),
    KEY_SIZE_TOO_SHORT("获取的密钥长度过短"),
    KEY_TOTAL_COUNT_IS_OVER_CACHE_MAX_SIZE("密钥总数超过上限"),
    KEY_PARAM_INVALID("密钥参数不合法"),
    // =============== 权限相关 ================
    KEY_SAE_NO_PERMISSION_ACQUIRE_THE_KEY("没有密钥获取权限");

    private final String errorMsg;

    private KeyErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

}
