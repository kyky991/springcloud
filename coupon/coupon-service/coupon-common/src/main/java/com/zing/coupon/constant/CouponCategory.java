package com.zing.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Zing
 * @date 2020-05-06
 */
@Getter
@AllArgsConstructor
public enum CouponCategory {

    /**
     * 优惠券分类
     */
    MANJIAN("满减券", "001"),
    ZHEKOU("折扣券", "002"),
    LIJIAN("立减券", "003"),
    ;

    private String description;

    private String code;

    public static CouponCategory of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(c -> c.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exist!"));
    }
}
