package com.zing.coupon.controller;

import com.alibaba.fastjson.JSON;
import com.zing.coupon.exception.CouponException;
import com.zing.coupon.executor.ExecuteManager;
import com.zing.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zing
 * @date 2020-05-08
 */
@Slf4j
@RestController
public class SettlementController {

    @Autowired
    private ExecuteManager executeManager;

    /**
     * <h2>优惠券结算</h2>
     * 127.0.0.1:7003/coupon-settlement/settlement/compute
     * 127.0.0.1:9000/api/coupon-settlement/settlement/compute
     */
    @PostMapping("/settlement/compute")
    public SettlementInfo computeRule(@RequestBody SettlementInfo settlement) throws CouponException {
        log.info("settlement: {}", JSON.toJSONString(settlement));
        return executeManager.computeRule(settlement);
    }

}
