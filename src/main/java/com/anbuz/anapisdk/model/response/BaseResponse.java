package com.anbuz.anapisdk.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;
    private String description;
}
