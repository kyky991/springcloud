package com.zing.coupon.converter;

import com.zing.coupon.constant.ProductLine;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 产品线枚举属性转换器
 *
 * @author Zing
 * @date 2020-05-06
 */
@Converter
public class ProductLineConverter implements AttributeConverter<ProductLine, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProductLine productLine) {
        return productLine.getCode();
    }

    @Override
    public ProductLine convertToEntityAttribute(Integer code) {
        return ProductLine.of(code);
    }
}
