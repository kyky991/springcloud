package com.zing.coupon.dao;

import com.zing.coupon.entity.CouponTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Zing
 * @date 2020-05-06
 */
public interface CouponTemplateDao extends JpaRepository<CouponTemplate, Integer> {

    /**
     * <h2>根据模板名称查询模板</h2>
     * where name = ...
     */
    CouponTemplate findByName(String name);

    /**
     * <h2>根据 available 和 expired 标记查找模板记录</h2>
     * where available = ... and expired = ...
     */
    List<CouponTemplate> findAllByAvailableAndExpired(Boolean available, Boolean expired);

    /**
     * <h2>根据 expired 标记查找模板记录</h2>
     * where expired = ...
     */
    List<CouponTemplate> findAllByExpired(Boolean expired);
}
