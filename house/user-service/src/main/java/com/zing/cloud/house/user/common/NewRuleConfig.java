package com.zing.cloud.house.user.common;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
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
        return new AvailabilityFilteringRule();
    }

}
