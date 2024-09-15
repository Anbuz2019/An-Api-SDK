package com.anbuz.anapisdk.service.impl;

import com.anbuz.anapisdk.model.enums.RequestMethodEnum;
import com.anbuz.anapisdk.model.request.BaseRequest;
import com.anbuz.anapisdk.model.response.BaseResponse;
import com.anbuz.anapisdk.service.ApiService;
import com.anbuz.anapisdk.service.BaseService;

public class ApiServiceImpl extends BaseService implements ApiService {

    @Override
    public BaseResponse<String> NameRequest(BaseRequest request) {
        request.setRequestUrl("/api/name");
        request.setRequestMethod(RequestMethodEnum.GET);
        return request(request);
    }

}
