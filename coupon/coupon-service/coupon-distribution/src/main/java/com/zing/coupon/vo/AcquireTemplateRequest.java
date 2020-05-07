package com.zing.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zing
 * @date 2020-05-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquireTemplateRequest {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 优惠券模板信息
     */
    private CouponTemplateSDK templateSDK;
}
