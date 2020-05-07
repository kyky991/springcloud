package com.zing.coupon.feign.hystrix;

import com.zing.coupon.feign.TemplateClient;
import com.zing.coupon.vo.CommonResponse;
import com.zing.coupon.vo.CouponTemplateSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Zing
 * @date 2020-05-07
 */
@Slf4j
@Component
public class TemplateClientHystrix implements TemplateClient {

    @Override
    public CommonResponse<List<CouponTemplateSDK>> findAllUsableTemplate() {
        log.error("[eureka-client-coupon-template] findAllUsableTemplate request error");
        return new CommonResponse<>(-1, "[eureka-client-coupon-template] request error",
                Collections.emptyList());
    }

    @Override
    public CommonResponse<Map<Integer, CouponTemplateSDK>> findIds2TemplateSDK(Collection<Integer> ids) {
        log.error("[eureka-client-coupon-template] findIds2TemplateSDK request error");
        return new CommonResponse<>(-1, "[eureka-client-coupon-template] request error",
                Collections.emptyMap());
    }
}
