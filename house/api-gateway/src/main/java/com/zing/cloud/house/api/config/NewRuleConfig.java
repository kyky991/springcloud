package com.zing.cloud.house.api.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class NewRuleConfig {

    @Autowired
    private IClientConfig ribbonClientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig clientConfig) {
        return new PingUrl(false, "/actuator/health");
    }

    @Bean
    public IRule ribbonRule(IClientConfig clientConfig) {
//        return new RandomRule();
        return new AvailabilityFilteringRule();
    }

}
