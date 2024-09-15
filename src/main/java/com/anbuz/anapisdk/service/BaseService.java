package com.anbuz.anapisdk.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.anbuz.anapisdk.client.AnApiClient;
import com.anbuz.anapisdk.exception.ApiException;
import com.anbuz.anapisdk.exception.ErrorCode;
import com.anbuz.anapisdk.model.request.BaseRequest;
import com.anbuz.anapisdk.model.response.BaseResponse;
import com.anbuz.anapisdk.utils.SignUtils;
import lombok.Data;
import cn.hutool.http.HttpResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class BaseService implements ApiService{

    private AnApiClient anApiClient;

    private String host = "http://localhost:3970";

    @Override
    public void setAnApiClient(AnApiClient anApiClient) {
        this.anApiClient = anApiClient;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 处理请求
     */
    @Override
    public <T extends Serializable> BaseResponse<T> request(BaseRequest request){
        checkConfig();
        HttpRequest httpRequest = getHttpRequestByRequestMethod(request);
        HttpResponse httpResponse = doRequest(httpRequest);
        String body = httpResponse.body();
        return JSONUtil.toBean(body, BaseResponse.class);
    }

    /**
     * 检查配置
     */
    public void checkConfig() throws ApiException {
        if (anApiClient == null || StringUtils.isAnyBlank(anApiClient.getAccessKey(), anApiClient.getSecretKey())) {
            throw new ApiException(ErrorCode.NO_AUTH_ERROR, "请先配置密钥AccessKey/SecretKey");
        }
    }

    /**
     * 通过请求方法，生成对应的HTTP请求
     */
    public HttpRequest getHttpRequestByRequestMethod(BaseRequest request){
        if (ObjectUtils.isEmpty(request)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求参数错误");
        }
        String path = request.getRequestUrl().trim();
        String method = request.getRequestMethod().getMethod().trim().toUpperCase();

        if (ObjectUtils.isEmpty(method)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求方法不存在");
        }
        if (StringUtils.isBlank(path)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求路径不存在");
        }

        if (path.startsWith(host)) {
            path = path.substring(host.length());
        }
        HttpRequest httpRequest;
        switch (method) {
            case "GET": {
                httpRequest = HttpRequest.get(host + splicingGetRequest(request, path));
                break;
            }
            case "POST": {
                httpRequest = HttpRequest.post(host + path);
                break;
            }
            default: {
                throw new ApiException(ErrorCode.OPERATION_ERROR, "不支持该请求");
            }
        }
        return httpRequest.body(JSONUtil.toJsonStr(request.getRequestParams())).addHeaders(getHeaders(request));
    }

    private Map<String, String> getHeaders(BaseRequest request) {
        Map<String, String> headers = new HashMap<>();
        headers.put("accessKey", anApiClient.getAccessKey());
        headers.put("method", request.getRequestMethod().getMethod());
        headers.put("url", request.getRequestUrl());
        headers.put("body", JSONUtil.toJsonStr(request.getRequestParams()));
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headers.put("sign", SignUtils.getSign(JSONUtil.toJsonStr(headers), anApiClient.getSecretKey()));
        return headers;
    }

    private String splicingGetRequest(BaseRequest request, String path) {
        StringBuilder urlBuilder = new StringBuilder(path);
        if (ObjectUtils.isNotEmpty(request.getRequestParams()) && !request.getRequestParams().isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, Object> entry : request.getRequestParams().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                urlBuilder.append(key).append("=").append(value).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        return urlBuilder.toString();
    }

    /**
     * 发送HTTP请求，获取响应结果
     */
    public HttpResponse doRequest(HttpRequest request) throws ApiException {
        try (HttpResponse httpResponse = request.execute()) {
            return httpResponse;
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
    }

}
