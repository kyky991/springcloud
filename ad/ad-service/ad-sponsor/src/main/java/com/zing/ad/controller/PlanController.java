package com.zing.ad.controller;

import com.alibaba.fastjson.JSON;
import com.zing.ad.entity.AdPlan;
import com.zing.ad.exception.AdException;
import com.zing.ad.service.IAdPlanService;
import com.zing.ad.vo.AdPlanGetRequest;
import com.zing.ad.vo.AdPlanRequest;
import com.zing.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Slf4j
@RestController
public class PlanController {

    @Autowired
    private IAdPlanService planService;

    @PostMapping("/plan/create")
    public AdPlanResponse createPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: createPlan -> {}", JSON.toJSONString(request));
        return planService.createAdPlan(request);
    }

    @PostMapping("/plan/list")
    public List<AdPlan> listPlan(@RequestBody AdPlanGetRequest request) throws AdException {
        log.info("ad-sponsor: listPlan -> {}", JSON.toJSONString(request));
        return planService.getAdPlanByIds(request);
    }

    @PutMapping("/plan/update")
    public AdPlanResponse updatePlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: updatePlan -> {}", JSON.toJSONString(request));
        return planService.updateAdPlan(request);
    }

    @DeleteMapping("/plan/delete")
    public void deletePlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: deletePlan -> {}", JSON.toJSONString(request));
        planService.deleteAdPlan(request);
    }
}
