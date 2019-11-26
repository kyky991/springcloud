package com.zing.ad.client;

import com.zing.ad.client.vo.AdPlan;
import com.zing.ad.client.vo.AdPlanGetRequest;
import com.zing.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Zing
 * @date 2019-11-25
 */
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/plan/list", method = RequestMethod.POST)
    public CommonResponse<List<AdPlan>> planList(@RequestBody AdPlanGetRequest request);

}
