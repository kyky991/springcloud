package com.zing.coupon.filter;

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
public class TokenFilter extends AbstractPreZuulFilter {

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        String token = request.getParameter("token");
        if (null == token) {
            log.error("error: token is empty");
            return fail(HttpStatus.UNAUTHORIZED.value(), "token is empty");
        }
        return success();
    }
}
