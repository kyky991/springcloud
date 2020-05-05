package com.zing.ad.service.impl;

import com.zing.ad.constant.Constants;
import com.zing.ad.entity.AdPlan;
import com.zing.ad.entity.AdUnit;
import com.zing.ad.entity.condition.AdUnitDistrict;
import com.zing.ad.entity.condition.AdUnitIt;
import com.zing.ad.entity.condition.AdUnitKeyword;
import com.zing.ad.entity.condition.CreativeUnit;
import com.zing.ad.exception.AdException;
import com.zing.ad.repository.AdPlanRepository;
import com.zing.ad.repository.AdUnitRepository;
import com.zing.ad.repository.condition.AdUnitDistrictRepository;
import com.zing.ad.repository.condition.AdUnitItRepository;
import com.zing.ad.repository.condition.AdUnitKeywordRepository;
import com.zing.ad.repository.condition.CreativeUnitRepository;
import com.zing.ad.service.IAdUnitService;
import com.zing.ad.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    @Autowired
    private AdPlanRepository planRepository;

    @Autowired
    private AdUnitRepository unitRepository;

    @Autowired
    private AdUnitKeywordRepository unitKeywordRepository;

    @Autowired
    private AdUnitItRepository unitItRepository;

    @Autowired
    private AdUnitDistrictRepository unitDistrictRepository;

    @Autowired
    private CreativeUnitRepository creativeUnitRepository;

    @Override
    public AdUnitResponse createAdUnit(AdUnitRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> plan = planRepository.findById(request.getPlanId());
        if (!plan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }

        AdUnit unit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (unit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_UNIT_NAME_ERROR);
        }

        AdUnit newUnit = unitRepository.save(new AdUnit(request.getPlanId(), request.getUnitName(), request.getPositionType(), request.getBudget()));

        return new AdUnitResponse(newUnit.getId(), newUnit.getUnitName());
    }

    @Override
    public AdUnitKeywordResponse createAdUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        List<Long> unitIds = request.getUnitKeywords().stream().map(AdUnitKeywordRequest.UnitKeyword::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();

        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(i -> unitKeywords.add(new AdUnitKeyword(i.getUnitId(), i.getKeyword())));

            ids = unitKeywordRepository.saveAll(unitKeywords).stream().map(AdUnitKeyword::getId).collect(Collectors.toList());
        }
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createAdUnitIt(AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream().map(AdUnitItRequest.UnitIt::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();

        List<AdUnitIt> unitIts = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitIts())) {
            request.getUnitIts().forEach(i -> unitIts.add(new AdUnitIt(i.getUnitId(), i.getItTag())));

            ids = unitItRepository.saveAll(unitIts).stream().map(AdUnitIt::getId).collect(Collectors.toList());
        }
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createAdUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistricts().stream().map(AdUnitDistrictRequest.UnitDistrict::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();

        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitDistricts())) {
            request.getUnitDistricts().forEach(i -> unitDistricts.add(new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())));

            ids = unitDistrictRepository.saveAll(unitDistricts).stream().map(AdUnitDistrict::getId).collect(Collectors.toList());
        }
        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
        List<Long> unitIds = request.getUnitItems().stream().map(CreativeUnitRequest.CreativeUnitItem::getUnitId).collect(Collectors.toList());
        List<Long> creativeIds = request.getUnitItems().stream().map(CreativeUnitRequest.CreativeUnitItem::getCreativeId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds) || !isRelatedCreativeExist(creativeIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();

        List<CreativeUnit> creativeUnits = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitItems())) {
            request.getUnitItems().forEach(i -> creativeUnits.add(new CreativeUnit(i.getCreativeId(), i.getUnitId())));

            ids = creativeUnitRepository.saveAll(creativeUnits).stream().map(CreativeUnit::getId).collect(Collectors.toList());
        }
        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }

        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }

        return creativeUnitRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
    }
}
