package com.zing.coupon.controller;

import com.alibaba.fastjson.JSON;
import com.zing.coupon.entity.CouponTemplate;
import com.zing.coupon.exception.CouponException;
import com.zing.coupon.service.IBuildTemplateService;
import com.zing.coupon.service.ITemplateBaseService;
import com.zing.coupon.vo.CouponTemplateSDK;
import com.zing.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 优惠券模板相关的功能控制器
 *
 * @author Zing
 * @date 2020-05-07
 */
@Slf4j
@RestController
public class CouponTemplateController {

    @Autowired
    private IBuildTemplateService buildTemplateService;

    @Autowired
    private ITemplateBaseService templateBaseService;

    /**
     * <h2>构建优惠券模板</h2>
     * 127.0.0.1:7001/coupon-template/template/build
     * 127.0.0.1:9000/api/coupon-template/template/build
     */
    @PostMapping("/template/build")
    public CouponTemplate buildTemplate(@RequestBody TemplateRequest request) throws CouponException {
        log.info("Build Template: {}", JSON.toJSONString(request));
        return buildTemplateService.buildTemplate(request);
    }

    /**
     * <h2>构造优惠券模板详情</h2>
     * 127.0.0.1:7001/coupon-template/template/info?id=1
     */
    @GetMapping("/template/info")
    public CouponTemplate buildTemplateInfo(Integer id) throws CouponException {
        log.info("Build Template Info For: {}", id);
        return templateBaseService.buildTemplateInfo(id);
    }

    /**
     * <h2>查找所有可用的优惠券模板</h2>
     * 127.0.0.1:7001/coupon-template/template/sdk/all
     */
    @GetMapping("/template/sdk/all")
    public List<CouponTemplateSDK> findAllUsableTemplate() {
        return templateBaseService.findAllUsableTemplate();
    }

    /**
     * <h2>获取模板 ids 到 CouponTemplateSDK 的映射</h2>
     * 127.0.0.1:7001/coupon-template/template/sdk/infos
     */
    @GetMapping("/template/sdk/infos")
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(@RequestParam("ids") Collection<Integer> ids) {
        log.info("FindIds2TemplateSDK: {}", JSON.toJSONString(ids));
        return templateBaseService.findIds2TemplateSDK(ids);
    }
}
