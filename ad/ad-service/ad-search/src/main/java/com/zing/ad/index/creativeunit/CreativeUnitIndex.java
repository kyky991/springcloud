package com.zing.ad.index.creativeunit;

import com.zing.ad.index.IndexAware;
import com.zing.ad.index.adunit.AdUnitObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Zing
 * @date 2019-11-25
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

    private static Map<String, CreativeUnitObject> objectMap;
    private static Map<Long, Set<Long>> creativeUnitMap;
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);

        Set<Long> unitIds = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isNotEmpty(unitIds)) {
            unitIds = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(), unitIds);
        }
        unitIds.add(value.getUnitId());

        Set<Long> creativeIds = creativeUnitMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeIds)) {
            creativeIds = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(), creativeIds);
        }
        creativeIds.add(value.getAdId());

        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex cannot support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);

        Set<Long> unitIds = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isNotEmpty(unitIds)) {
            unitIds.remove(value.getUnitId());
        }

        Set<Long> creativeIds = creativeUnitMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeIds)) {
            creativeIds.remove(value.getAdId());
        }
        log.info("after delete: {}", objectMap);
    }

    public List<Long> selectAds(List<AdUnitObject> unitObjects) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return Collections.emptyList();
        }

        List<Long> result = new ArrayList<>();

        for (AdUnitObject unitObject : unitObjects) {
            Set<Long> adIds = unitCreativeMap.get(unitObject.getUnitId());
            if (CollectionUtils.isNotEmpty(adIds)) {
                result.addAll(adIds);
            }
        }

        return result;
    }
}

