package com.zing.ad.service;

import com.zing.ad.entity.AdPlan;
import com.zing.ad.exception.AdException;
import com.zing.ad.vo.AdPlanGetRequest;
import com.zing.ad.vo.AdPlanRequest;
import com.zing.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @author Zing
 * @date 2019-11-22
 */
public interface IAdPlanService {

    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
