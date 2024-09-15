package com.anbuz.anapisdk.service;

import com.anbuz.anapisdk.client.AnApiClient;
import com.anbuz.anapisdk.model.request.BaseRequest;
import com.anbuz.anapisdk.model.response.BaseResponse;

import java.io.Serializable;

public interface ApiService {

    public <T extends Serializable> BaseResponse<T> request(BaseRequest request);

    public BaseResponse<String> NameRequest(BaseRequest request);

    public void setAnApiClient(AnApiClient anApiClient);

    void setHost(String host);
}
