package com.zing.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zing
 * @date 2020-05-05
 */
@Slf4j
@Component
public class AccessLogFilter extends AbstractPostZuulFilter {

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();

        long startTime = (long) context.get("startTime");
        long duration = System.currentTimeMillis() - startTime;

        log.info("uri: {}, duration: {}", request.getRequestURI(), duration);

        return success();
    }
}
