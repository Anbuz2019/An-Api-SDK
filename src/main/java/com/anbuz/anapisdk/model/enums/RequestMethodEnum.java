package com.anbuz.anapisdk.model.enums;

public enum RequestMethodEnum {

    GET(0, "GET"),
    POST(1, "POST");

    private final int value;
    private final String method;
    RequestMethodEnum(int value, String method) {
        this.value = value;
        this.method = method;
    }

    public int getValue() {
        return value;
    }
    public String getMethod() {
        return method;
    }

}
