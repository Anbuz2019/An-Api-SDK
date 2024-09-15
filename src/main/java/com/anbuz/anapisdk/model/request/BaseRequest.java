package com.anbuz.anapisdk.model.request;

import com.anbuz.anapisdk.model.enums.RequestMethodEnum;
import lombok.Data;

import java.util.Map;

@Data
public class BaseRequest {

    private RequestMethodEnum requestMethod;

    private String requestUrl;

    private Map<String, Object> requestParams;
}
