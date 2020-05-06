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
public enum DistributeTarget {

    /**
     * 分发目标
     */
    SINGLE("单用户", 1),
    MULTI("多用户", 2),
    ;

    private String description;

    private Integer code;

    public static DistributeTarget of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(c -> c.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exist!"));
    }
}