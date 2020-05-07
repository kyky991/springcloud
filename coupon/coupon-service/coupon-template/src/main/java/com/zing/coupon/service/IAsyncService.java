package com.zing.coupon.service;

import com.zing.coupon.entity.CouponTemplate;

/**
 * 异步服务接口定义
 *
 * @author Zing
 * @date 2020-05-07
 */
public interface IAsyncService {

    /**
     * 根据模板异步的创建优惠券码
     *
     * @param template {@link CouponTemplate} 优惠券模板实体
     */
    void asyncConstructCouponByTemplate(CouponTemplate template);

}
