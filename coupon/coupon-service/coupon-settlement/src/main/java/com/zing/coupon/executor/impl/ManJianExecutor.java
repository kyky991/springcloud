package com.zing.coupon.executor.impl;

import com.zing.coupon.constant.RuleFlag;
import com.zing.coupon.executor.AbstractExecutor;
import com.zing.coupon.executor.RuleExecutor;
import com.zing.coupon.vo.CouponTemplateSDK;
import com.zing.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 满减优惠券结算规则执行器
 *
 * @author Zing
 * @date 2020-05-08
 */
@Slf4j
@Component
public class ManJianExecutor extends AbstractExecutor implements RuleExecutor {

    @Override
    public RuleFlag ruleConfig() {
        return RuleFlag.MANJIAN;
    }

    @Override
    public SettlementInfo computeRule(SettlementInfo settlement) {
        double goodsSum = retain2Decimals(goodsCostSum(settlement.getGoodsInfos()));
        SettlementInfo probability = processGoodsTypeNotSatisfy(settlement, goodsSum);
        if (null != probability) {
            log.debug("ManJian Template Is Not Match To GoodsType!");
            return probability;
        }

        // 判断满减是否符合折扣标准
        CouponTemplateSDK templateSDK = settlement.getCouponAndTemplateInfos().get(0).getTemplate();
        double base = (double) templateSDK.getRule().getDiscount().getBase();
        double quota = (double) templateSDK.getRule().getDiscount().getQuota();

        // 如果不符合标准, 则直接返回商品总价
        if (goodsSum < base) {
            log.debug("Current Goods Cost Sum < ManJian Coupon Base!");
            settlement.setCost(goodsSum);
            settlement.setCouponAndTemplateInfos(Collections.emptyList());
            return settlement;
        }

        // 计算使用优惠券之后的价格 - 结算
        settlement.setCost(retain2Decimals(Math.max((goodsSum - quota), minCost())));

        log.debug("Use ManJian Coupon Make Goods Cost From {} To {}", goodsSum, settlement.getCost());

        return settlement;
    }
}
