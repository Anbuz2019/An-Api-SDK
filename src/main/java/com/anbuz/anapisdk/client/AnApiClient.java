package com.anbuz.anapisdk.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnApiClient {
    /**
     * 访问id
     */
    private String accessKey;
    /**
     * 访问密钥
     */
    private String secretKey;
}
