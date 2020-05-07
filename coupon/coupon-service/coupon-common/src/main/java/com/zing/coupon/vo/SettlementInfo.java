package com.zing.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 结算信息
 *
 * @author Zing
 * @date 2020-05-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementInfo {

    /**
     * 用户信息
     */
    private Long userId;

    /**
     * 商品信息
     */
    private List<GoodsInfo> goodsInfos;

    /**
     * 优惠券列表
     */
    private List<CouponAndTemplateInfo> couponAndTemplateInfos;

    /**
     * 是否使结算生效，即核销
     */
    private Boolean employ;

    /**
     * 结果结算金额
     */
    private Double cost;

    /**
     * 优惠券和模板信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CouponAndTemplateInfo {

        /**
         * Coupon 主键
         */
        private Integer id;

        /**
         * 模板信息
         */
        private CouponTemplateSDK template;
    }
}
