package com.anbuz.anapisdk.config;

import com.anbuz.anapisdk.client.AnApiClient;
import com.anbuz.anapisdk.service.ApiService;
import com.anbuz.anapisdk.service.impl.ApiServiceImpl;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "an-api.client")
public class AnApiConfig {

    /**
     * 访问id
     */
    private String accessKey;

    /**
     * 访问密钥
     */
    private String secretKey;

    /**
     * 网关
     */
    private String host;

    @Bean
    public AnApiClient anApiClient() {
        return new AnApiClient(accessKey, secretKey);
    }

    @Bean
    public ApiService anApiService() {
        ApiService apiService = new ApiServiceImpl();
        apiService.setAnApiClient(new AnApiClient(accessKey, secretKey));
        if (StringUtils.isNotBlank(host)) {
            apiService.setHost(host);
        }
        return apiService;
    }
}
