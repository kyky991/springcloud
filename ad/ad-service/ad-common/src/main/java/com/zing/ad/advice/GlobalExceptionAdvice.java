package com.zing.ad.advice;

import com.zing.ad.exception.AdException;
import com.zing.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zing
 * @date 2019-11-22
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest request, AdException exp) {
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        response.setData(exp.getMessage());
        return response;
    }

}
