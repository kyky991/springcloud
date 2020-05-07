package com.zing.coupon.service;

import com.zing.coupon.entity.CouponTemplate;
import com.zing.coupon.exception.CouponException;
import com.zing.coupon.vo.TemplateRequest;

/**
 * 构建优惠券模板接口定义
 *
 * @author Zing
 * @date 2020-05-07
 */
public interface IBuildTemplateService {

    /**
     * 创建优惠券模板
     *
     * @param request {@link TemplateRequest} 模板信息请求对象
     * @return {@link CouponTemplate} 优惠券模板实体
     */
    CouponTemplate buildTemplate(TemplateRequest request) throws CouponException;
}
