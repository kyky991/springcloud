package com.zing.cloud.house.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 既支持直连又支持服务发现的调用
 */
@Component
public class GenericRest {

    private static final String DIRECT_FLAG = "direct://";

    @Autowired
    private RestTemplate lbRestTemplate;

    @Autowired
    private RestTemplate directRestTemplate;

    public <T> ResponseEntity<T> post(String url, Object body, ParameterizedTypeReference<T> responseType) {
        RestTemplate restTemplate = getRestTemplate(url);
        url = url.replace(DIRECT_FLAG, "");
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body), responseType);
    }

    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType) {
        RestTemplate restTemplate = getRestTemplate(url);
        url = url.replace(DIRECT_FLAG, "");
        return restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType);
    }

    private RestTemplate getRestTemplate(String url) {
        if (url.contains(DIRECT_FLAG)) {
            return directRestTemplate;
        } else {
            return lbRestTemplate;
        }
    }
}
