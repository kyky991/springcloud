package com.zing.coupon.converter;

import com.zing.coupon.constant.DistributeTarget;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 分发目标枚举属性转换器
 *
 * @author Zing
 * @date 2020-05-06
 */
@Converter
public class DistributeTargetConverter implements AttributeConverter<DistributeTarget, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DistributeTarget distributeTarget) {
        return distributeTarget.getCode();
    }

    @Override
    public DistributeTarget convertToEntityAttribute(Integer code) {
        return DistributeTarget.of(code);
    }
}
