package com.zing.coupon.filter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zing
 * @date 2020-05-05
 */
@Slf4j
@Component
public class RateLimiterFilter extends AbstractPreZuulFilter {

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(2.0);

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();

        if (RATE_LIMITER.tryAcquire()) {
            log.info("get rate token success");
            return success();
        } else {
            log.error("rate limit: {}", request.getRequestURI());
            return fail(HttpStatus.FORBIDDEN.value(), "rate limit");
        }
    }
}
