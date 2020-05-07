package com.zing.coupon.service.impl;

import com.zing.coupon.dao.CouponTemplateDao;
import com.zing.coupon.entity.CouponTemplate;
import com.zing.coupon.exception.CouponException;
import com.zing.coupon.service.IAsyncService;
import com.zing.coupon.service.IBuildTemplateService;
import com.zing.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zing
 * @date 2020-05-07
 */
@Slf4j
@Service
public class BuildTemplateServiceImpl implements IBuildTemplateService {

    @Autowired
    private IAsyncService asyncService;

    @Autowired
    private CouponTemplateDao couponTemplateDao;

    @Override
    public CouponTemplate buildTemplate(TemplateRequest request) throws CouponException {
        // 参数合法性校验
        if (!request.validate()) {
            throw new CouponException("BuildTemplate Param Is Not Valid!");
        }

        // 判断同名的优惠券模板是否存在
        if (null != couponTemplateDao.findByName(request.getName())) {
            throw new CouponException("Exist Same Name Template!");
        }

        // 构造 CouponTemplate 并保存到数据库中
        CouponTemplate template = requestToTemplate(request);
        template = couponTemplateDao.save(template);

        // 根据优惠券模板异步生成优惠券码
        asyncService.asyncConstructCouponByTemplate(template);

        return template;
    }

    private CouponTemplate requestToTemplate(TemplateRequest request) {
        return new CouponTemplate(request.getName(), request.getLogo(), request.getDesc(), request.getCategory(),
                request.getProductLine(), request.getCount(), request.getUserId(), request.getTarget(), request.getRule());
    }
}
