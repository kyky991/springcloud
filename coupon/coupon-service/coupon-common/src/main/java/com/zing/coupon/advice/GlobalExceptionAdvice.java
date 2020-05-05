package com.zing.coupon.advice;

import com.zing.coupon.exception.CouponException;
import com.zing.coupon.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zing
 * @date 2020-05-05
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = CouponException.class)
    public CommonResponse<String> handleCouponException(HttpServletRequest request, CouponException ex) {
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        response.setData(ex.getMessage());
        return response;
    }

}
