package com.zing.coupon.controller;

import com.zing.coupon.exception.CouponException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zing
 * @date 2020-05-07
 */
@Slf4j
@RestController
public class HealthCheck {

    /**
     * 服务发现客户端
     */
    @Autowired
    private DiscoveryClient client;

    /**
     * 服务注册接口, 提供了获取服务 id 的方法
     */
    @Autowired
    private Registration registration;

    /**
     * <h2>健康检查接口</h2>
     * 127.0.0.1:7001/coupon-template/health
     */
    @GetMapping("/health")
    public String health() {
        log.debug("view health api");
        return "CouponTemplate Is OK!";
    }

    /**
     * <h2>异常测试接口</h2>
     * 127.0.0.1:7001/coupon-template/exception
     */
    @GetMapping("/exception")
    public String exception() throws CouponException {
        log.debug("view exception api");
        throw new CouponException("CouponTemplate Has Some Problem");
    }

    @GetMapping("/info")
    public List<Map<String, Object>> info() {
        List<ServiceInstance> instances = client.getInstances(registration.getServiceId());
        List<Map<String, Object>> result = new ArrayList<>(instances.size());

        instances.forEach(i -> {
            Map<String, Object> info = new HashMap<>(2);
            info.put("serviceId", i.getServiceId());
            info.put("instanceId", i.getInstanceId());
            info.put("port", i.getPort());
            result.add(info);
        });

        return result;
    }

}
