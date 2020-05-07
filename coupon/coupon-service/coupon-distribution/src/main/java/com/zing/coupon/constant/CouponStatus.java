package com.zing.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Zing
 * @date 2020-05-07
 */
@Getter
@AllArgsConstructor
public enum CouponStatus {

    /**
     * 用户优惠券的状态
     */
    USABLE("可用的", 1),
    USED("已使用的", 2),
    EXPIRED("过期的(未被使用的)", 3),
    ;

    /**
     * 优惠券状态描述信息
     */
    private String description;

    /**
     * 优惠券状态编码
     */
    private Integer code;

    public static CouponStatus of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(c -> c.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exist!"));
    }
}
