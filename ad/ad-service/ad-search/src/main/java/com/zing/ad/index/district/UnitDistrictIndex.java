package com.zing.ad.index.district;

import com.zing.ad.index.IndexAware;
import com.zing.ad.search.vo.feature.DistrictFeature;
import com.zing.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @author Zing
 * @date 2019-11-25
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> districtUnitMap;
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }
        Set<Long> result = districtUnitMap.get(key);
        if (result == null) {
            return Collections.emptySet();
        }
        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitDistrictIndex, before add: {}", unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            districts.add(key);
        }

        log.info("UnitDistrictIndex, after add: {}", unitDistrictMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("UnitDistrictIndex cannot support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitDistrictIndex, before delete: {}", unitDistrictMap);

        Set<Long> unitIds = CommonUtils.getOrCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            districts.remove(key);
        }

        log.info("UnitDistrictIndex, after delete: {}", unitDistrictMap);
    }

    public boolean match(Long unitId, List<DistrictFeature.ProvinceAndCity> districts) {
        if (unitDistrictMap.containsKey(unitId)) {
            Set<String> unitDistricts = unitDistrictMap.get(unitId);
            if (CollectionUtils.isNotEmpty(unitDistricts)) {
                List<String> list = districts.stream()
                        .map(d -> String.join("-", d.getProvince(), d.getCity()))
                        .collect(Collectors.toList());
                return CollectionUtils.isSubCollection(list, unitDistricts);
            }
        }
        return false;
    }
}

