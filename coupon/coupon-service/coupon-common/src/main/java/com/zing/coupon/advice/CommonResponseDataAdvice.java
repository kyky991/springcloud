package com.zing.coupon.advice;

import com.zing.coupon.annotation.IgnoreResponseAdvice;
import com.zing.coupon.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Zing
 * @date 2020-05-05
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果当前方法所在的类标识了 @IgnoreResponseAdvice 不需要处理
        if (returnType.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        if (returnType.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        // 对响应进行处理
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        CommonResponse<Object> rsp = new CommonResponse<>(0, "");
        if (null == body) {
            return response;
        } else if (body instanceof CommonResponse) {
            rsp = (CommonResponse<Object>) body;
        } else {
            rsp.setData(body);
        }
        return rsp;
    }
}
