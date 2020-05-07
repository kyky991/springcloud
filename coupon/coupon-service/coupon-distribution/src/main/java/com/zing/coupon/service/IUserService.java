package com.zing.coupon.service;

import com.zing.coupon.entity.Coupon;
import com.zing.coupon.exception.CouponException;
import com.zing.coupon.vo.AcquireTemplateRequest;
import com.zing.coupon.vo.CouponTemplateSDK;
import com.zing.coupon.vo.SettlementInfo;

import java.util.List;

/**
 * <h1>用户服务相关的接口定义</h1>
 * 1. 用户三类状态优惠券信息展示服务
 * 2. 查看用户当前可以领取的优惠券模板 - coupon-template 微服务配合实现
 * 3. 用户领取优惠券服务
 * 4. 用户消费优惠券服务 - coupon-settlement 微服务配合实现
 *
 * @author Zing
 * @date 2020-05-07
 */
public interface IUserService {

    /**
     * <h2>根据用户 id 和状态查询优惠券记录</h2>
     *
     * @param userId 用户 id
     * @param status 优惠券状态
     * @return {@link Coupon}s
     */
    List<Coupon> findCouponsByStatus(Long userId, Integer status) throws CouponException;

    /**
     * <h2>根据用户 id 查找当前可以领取的优惠券模板</h2>
     *
     * @param userId 用户 id
     * @return {@link CouponTemplateSDK}s
     */
    List<CouponTemplateSDK> findAvailableTemplate(Long userId) throws CouponException;

    /**
     * <h2>用户领取优惠券</h2>
     *
     * @param request {@link AcquireTemplateRequest}
     * @return {@link Coupon}
     */
    Coupon acquireTemplate(AcquireTemplateRequest request) throws CouponException;

    /**
     * <h2>结算(核销)优惠券</h2>
     *
     * @param info {@link SettlementInfo}
     * @return {@link SettlementInfo}
     */
    SettlementInfo settlement(SettlementInfo info) throws CouponException;
}
