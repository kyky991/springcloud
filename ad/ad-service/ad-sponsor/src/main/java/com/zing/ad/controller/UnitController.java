package com.zing.ad.controller;

import com.alibaba.fastjson.JSON;
import com.zing.ad.exception.AdException;
import com.zing.ad.service.IAdUnitService;
import com.zing.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Slf4j
@RestController
public class UnitController {

    @Autowired
    private IAdUnitService unitService;

    @PostMapping("/unit/create")
    public AdUnitResponse createUnit(@RequestBody AdUnitRequest request) throws AdException {
        log.info("ad-sponsor: createUnit -> {}", JSON.toJSONString(request));
        return unitService.createAdUnit(request);
    }

    @PostMapping("/unitKeyword/create")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest request) throws AdException {
        log.info("ad-sponsor: createUnitKeyword -> {}", JSON.toJSONString(request));
        return unitService.createAdUnitKeyword(request);
    }

    @PostMapping("/unitIt/create")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest request) throws AdException {
        log.info("ad-sponsor: createUnitIt -> {}", JSON.toJSONString(request));
        return unitService.createAdUnitIt(request);
    }

    @PostMapping("/unitDistrict/create")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest request) throws AdException {
        log.info("ad-sponsor: createUnitDistrict -> {}", JSON.toJSONString(request));
        return unitService.createAdUnitDistrict(request);
    }

    @PostMapping("/creativeUnit/create")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException {
        log.info("ad-sponsor: createCreativeUnit -> {}", JSON.toJSONString(request));
        return unitService.createCreativeUnit(request);
    }
}
