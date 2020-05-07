package com.zing.coupon.dao;

import com.zing.coupon.constant.CouponStatus;
import com.zing.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Zing
 * @date 2020-05-07
 */
public interface CouponDao extends JpaRepository<Coupon, Integer> {

    /**
     * <h2>根据 userId + 状态寻找优惠券记录</h2>
     * where userId = ... and status = ...
     */
    List<Coupon> findAllByUserIdAndStatus(Long userId, CouponStatus status);
}
