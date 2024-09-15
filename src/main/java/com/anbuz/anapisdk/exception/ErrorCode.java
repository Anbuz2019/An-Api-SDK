package com.anbuz.anapisdk.exception;

public enum ErrorCode {

    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求的数据不存在"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败");

    private int value;
    private String message;

    ErrorCode(int value, String message) {
        this.value = value;
        this.message = message;
    }

    int getValue() {
        return value;
    }
}
