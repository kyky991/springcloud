package com.zing.ad.service.impl;

import com.zing.ad.constant.CommonStatus;
import com.zing.ad.constant.Constants;
import com.zing.ad.entity.AdPlan;
import com.zing.ad.entity.AdUser;
import com.zing.ad.exception.AdException;
import com.zing.ad.repository.AdPlanRepository;
import com.zing.ad.repository.AdUserRepository;
import com.zing.ad.service.IAdPlanService;
import com.zing.ad.utils.CommonUtils;
import com.zing.ad.vo.AdPlanGetRequest;
import com.zing.ad.vo.AdPlanRequest;
import com.zing.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Slf4j
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    @Autowired
    private AdUserRepository userRepository;

    @Autowired
    private AdPlanRepository planRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdUser> user = userRepository.findById(request.getUserId());
        if (!user.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }

        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_PLAN_NAME_ERROR);
        }

        AdPlan plan = planRepository.save(new AdPlan(request.getUserId(), request.getPlanName(),
                CommonUtils.parseDate(request.getStartDate()), CommonUtils.parseDate(request.getEndDate())));

        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return planRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }

        if (request.getPlanName() != null) {
            plan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            plan.setStartDate(CommonUtils.parseDate(request.getStartDate()));
        }
        if (request.getEndDate() != null) {
            plan.setEndDate(CommonUtils.parseDate(request.getEndDate()));
        }

        plan.setUpdateTime(new Date());
        plan = planRepository.save(plan);

        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }

        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);
    }
}
