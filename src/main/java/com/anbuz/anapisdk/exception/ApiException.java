package com.anbuz.anapisdk.exception;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getValue();
    }

    public int getCode() {
        return code;
    }

}
