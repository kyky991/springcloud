package com.zing.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Zing
 * @date 2020-05-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public CommonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
