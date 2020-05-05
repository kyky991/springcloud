package com.zing.ad.client;

import com.zing.ad.client.vo.AdPlan;
import com.zing.ad.client.vo.AdPlanGetRequest;
import com.zing.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zing
 * @date 2019-11-25
 */
@Component
public class SponsorClientHystrix implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlan>> planList(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
