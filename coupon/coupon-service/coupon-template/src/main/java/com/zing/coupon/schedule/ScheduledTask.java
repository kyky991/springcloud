package com.zing.coupon.schedule;

import com.zing.coupon.dao.CouponTemplateDao;
import com.zing.coupon.entity.CouponTemplate;
import com.zing.coupon.vo.TemplateRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时清理已过期的优惠券模板
 *
 * @author Zing
 * @date 2020-05-07
 */
@Slf4j
@Component
public class ScheduledTask {

    @Autowired
    private CouponTemplateDao couponTemplateDao;

    /**
     * 下线已过期的优惠券模板
     */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void offlineCouponTemplate() {
        log.info("Start To Expire CouponTemplate");

        List<CouponTemplate> templates = couponTemplateDao.findAllByExpired(false);
        if (CollectionUtils.isEmpty(templates)) {
            log.info("Done To Expire CouponTemplate.");
            return;
        }

        Date now = new Date();
        List<CouponTemplate> expiredTemplates = new ArrayList<>(templates.size());
        templates.forEach(t -> {
            // 根据优惠券模板规则中的 "过期规则" 校验模板是否过期
            TemplateRule rule = t.getRule();
            if (rule.getExpiration().getDeadline() < now.getTime()) {
                t.setExpired(true);
                expiredTemplates.add(t);
            }
        });
        if (CollectionUtils.isNotEmpty(expiredTemplates)) {
            log.info("Expired CouponTemplate Num: {}", couponTemplateDao.saveAll(expiredTemplates));
        }

        log.info("Done To Expire CouponTemplate.");
    }
}
