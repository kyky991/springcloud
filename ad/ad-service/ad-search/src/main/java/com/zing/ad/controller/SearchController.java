package com.zing.ad.controller;

import com.alibaba.fastjson.JSON;
import com.zing.ad.annotation.IgnoreResponseAdvice;
import com.zing.ad.client.SponsorClient;
import com.zing.ad.client.vo.AdPlan;
import com.zing.ad.client.vo.AdPlanGetRequest;
import com.zing.ad.search.ISearch;
import com.zing.ad.search.vo.SearchRequest;
import com.zing.ad.search.vo.SearchResponse;
import com.zing.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Zing
 * @date 2019-11-25
 */
@Slf4j
@RestController
public class SearchController {

    @Autowired
    private ISearch search;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SponsorClient sponsorClient;

    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request) {
        log.info("ad-search: fetchAds -> {}", JSON.toJSONString(request));
        return search.fetch(request);
    }

    @IgnoreResponseAdvice
    @PostMapping("/plan/list/rest")
    public CommonResponse<List<AdPlan>> planList(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: planList -> {}", JSON.toJSONString(request));
        return restTemplate.postForEntity("http://eureka-client-ad-sponsor/ad-sponsor/plan/list", request, CommonResponse.class).getBody();
    }

    @IgnoreResponseAdvice
    @PostMapping("/plan/list/feign")
    public CommonResponse<List<AdPlan>> feignPlanList(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: feignPlanList -> {}", JSON.toJSONString(request));
        return sponsorClient.planList(request);
    }
}
