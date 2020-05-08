package com.zing.coupon.executor.impl;

import com.zing.coupon.constant.RuleFlag;
import com.zing.coupon.executor.AbstractExecutor;
import com.zing.coupon.executor.RuleExecutor;
import com.zing.coupon.vo.CouponTemplateSDK;
import com.zing.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 折扣优惠券结算规则执行器
 *
 * @author Zing
 * @date 2020-05-08
 */
@Slf4j
@Component
public class ZheKouExecutor extends AbstractExecutor implements RuleExecutor {

    @Override
    public RuleFlag ruleConfig() {
        return RuleFlag.ZHEKOU;
    }

    @Override
    public SettlementInfo computeRule(SettlementInfo settlement) {
        double goodsSum = retain2Decimals(goodsCostSum(settlement.getGoodsInfos()));
        SettlementInfo probability = processGoodsTypeNotSatisfy(settlement, goodsSum);
        if (null != probability) {
            log.debug("ZheKou Template Is Not Match To GoodsType!");
            return probability;
        }

        // 折扣优惠券可以直接使用, 没有门槛
        CouponTemplateSDK templateSDK = settlement.getCouponAndTemplateInfos().get(0).getTemplate();
        double quota = (double) templateSDK.getRule().getDiscount().getQuota();

        // 计算使用优惠券之后的价格 - 结算
        settlement.setCost(Math.max(retain2Decimals((goodsSum * (quota * 1.0 / 100))), minCost()));

        log.debug("Use ZheKou Coupon Make Goods Cost From {} To {}", goodsSum, settlement.getCost());

        return settlement;
    }
}
