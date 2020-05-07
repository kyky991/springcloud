package com.zing.coupon.service.impl;

import com.zing.coupon.dao.CouponTemplateDao;
import com.zing.coupon.entity.CouponTemplate;
import com.zing.coupon.exception.CouponException;
import com.zing.coupon.service.ITemplateBaseService;
import com.zing.coupon.vo.CouponTemplateSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Zing
 * @date 2020-05-07
 */
@Slf4j
@Service
public class TemplateBaseServiceImpl implements ITemplateBaseService {

    @Autowired
    private CouponTemplateDao couponTemplateDao;

    @Override
    public CouponTemplate buildTemplateInfo(Integer id) throws CouponException {
        Optional<CouponTemplate> template = couponTemplateDao.findById(id);
        if (!template.isPresent()) {
            throw new CouponException("Template Is Not Exist: " + id);
        }
        return template.get();
    }

    @Override
    public List<CouponTemplateSDK> findAllUsableTemplate() {
        List<CouponTemplate> templates = couponTemplateDao.findAllByAvailableAndExpired(true, false);
        return templates.stream().map(this::template2TemplateSDK).collect(Collectors.toList());
    }

    @Override
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(Collection<Integer> ids) {
        List<CouponTemplate> templates = couponTemplateDao.findAllById(ids);
        return templates.stream().map(this::template2TemplateSDK)
                .collect(Collectors.toMap(CouponTemplateSDK::getId, Function.identity()));
    }

    private CouponTemplateSDK template2TemplateSDK(CouponTemplate template) {
        return new CouponTemplateSDK(template.getId(), template.getName(), template.getLogo(), template.getDesc(),
                template.getCategory().getCode(), template.getProductLine().getCode(), template.getKey(),
                template.getTarget().getCode(), template.getRule());
    }
}
