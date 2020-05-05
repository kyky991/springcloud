package com.zing.ad.controller;

import com.alibaba.fastjson.JSON;
import com.zing.ad.service.ICreativeService;
import com.zing.ad.vo.CreativeRequest;
import com.zing.ad.vo.CreativeResponse;
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
public class CreativeController {

    @Autowired
    private ICreativeService creativeService;

    @PostMapping("/creative/create")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) {
        log.info("ad-sponsor: createCreative -> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }

}
