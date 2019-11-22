package com.zing.ad.service;

import com.zing.ad.exception.AdException;
import com.zing.ad.vo.*;

/**
 * @author Zing
 * @date 2019-11-22
 */
public interface IAdUnitService {

    AdUnitResponse createAdUnit(AdUnitRequest request) throws AdException;

    AdUnitKeywordResponse createAdUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    AdUnitItResponse createAdUnitIt(AdUnitItRequest request) throws AdException;

    AdUnitDistrictResponse createAdUnitDistrict(AdUnitDistrictRequest request) throws AdException;

    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;

}
