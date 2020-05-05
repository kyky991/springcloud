package com.zing.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Zing
 * @date 2020-05-05
 */
@Slf4j
@Component
public class PreRequestFilter extends AbstractPreZuulFilter {

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    protected Object cRun() {
        context.set("startTime", System.currentTimeMillis());
        return success();
    }
}
