package com.zing.coupon.feign.hystrix;

import com.zing.coupon.exception.CouponException;
import com.zing.coupon.feign.SettlementClient;
import com.zing.coupon.vo.CommonResponse;
import com.zing.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Zing
 * @date 2020-05-08
 */
@Slf4j
@Component
public class SettlementClientHystrix implements SettlementClient {

    @Override
    public CommonResponse<SettlementInfo> computeRule(SettlementInfo settlement) throws CouponException {
        log.error("[eureka-client-coupon-settlement] computeRule request error");

        settlement.setEmploy(false);
        settlement.setCost(-1.0);

        return new CommonResponse<>(-1, "[eureka-client-coupon-settlement] request error", settlement);
    }
}
