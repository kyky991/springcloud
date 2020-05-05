package com.zing.coupon.filter;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * @author Zing
 * @date 2020-05-05
 */
public abstract class AbstractPostZuulFilter extends AbstractZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

}
