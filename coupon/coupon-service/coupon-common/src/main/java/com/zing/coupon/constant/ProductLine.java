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
public enum ProductLine {

    /**
     * 产品线
     */
    DAMAO("大猫", 1),
    DABAO("大宝", 2),
    ;

    private String description;

    private Integer code;

    public static ProductLine of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(c -> c.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exist!"));
    }
}