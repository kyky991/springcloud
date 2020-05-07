package com.zing.coupon.converter;

import com.alibaba.fastjson.JSON;
import com.zing.coupon.vo.TemplateRule;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 优惠券规则属性转换器
 *
 * @author Zing
 * @date 2020-05-06
 */
@Converter
public class TemplateRuleConverter implements AttributeConverter<TemplateRule, String> {

    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return JSON.toJSONString(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String rule) {
        return JSON.parseObject(rule, TemplateRule.class);
    }
}
