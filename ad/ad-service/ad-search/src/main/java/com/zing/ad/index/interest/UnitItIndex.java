package com.zing.ad.index.interest;

import com.zing.ad.index.IndexAware;
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

/**
 * @author Zing
 * @date 2019-11-25
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> itUnitMap;
    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }
        Set<Long> result = itUnitMap.get(key);
        if (result == null) {
            return Collections.emptySet();
        }
        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitItIndex, before add: {}", unitItMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> its = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            its.add(key);
        }

        log.info("UnitItIndex, after add: {}", unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("UnitItIndex cannot support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitItIndex, before delete: {}", unitItMap);

        Set<Long> unitIds = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> its = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            its.remove(key);
        }

        log.info("UnitItIndex, after delete: {}", unitItMap);
    }

    public boolean match(Long unitId, List<String> its) {
        if (unitItMap.containsKey(unitId)) {
            Set<String> unitIts = unitItMap.get(unitId);
            if (CollectionUtils.isNotEmpty(unitIts)) {
                return CollectionUtils.isSubCollection(its, unitIts);
            }
        }
        return false;
    }
}

