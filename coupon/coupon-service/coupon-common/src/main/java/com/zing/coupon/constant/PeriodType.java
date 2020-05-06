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
public enum PeriodType {

    /**
     * 产品线
     */
    REGULAR("固定的(固定日期)", 1),
    SHIFT("变动的(领取之日开始计算)", 2),
    ;

    private String description;

    private Integer code;

    public static PeriodType of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(c -> c.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exist!"));
    }
}