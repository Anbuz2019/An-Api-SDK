package com.anbuz.anapisdk;

import com.anbuz.anapisdk.client.AnApiClient;
import com.anbuz.anapisdk.config.AnApiConfig;
import com.anbuz.anapisdk.model.request.BaseRequest;
import com.anbuz.anapisdk.model.response.BaseResponse;
import com.anbuz.anapisdk.service.ApiService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = {AnApiConfig.class})
public class TryReq {

    @Resource
    ApiService anApiService;

    @Test
    public void trySend() {
        AnApiClient anApiClient = new AnApiClient("1", "1");
        anApiService.setAnApiClient(anApiClient);
        BaseRequest baseRequest = new BaseRequest();
        Map<String, Object> params = new HashMap<>();
        params.put("name", "anbuz");
        baseRequest.setRequestParams(params);
        BaseResponse<String> stringBaseResponse = anApiService.NameRequest(baseRequest);
        System.out.println(stringBaseResponse);
    }
}
