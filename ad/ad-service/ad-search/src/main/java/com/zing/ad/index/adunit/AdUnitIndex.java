package com.zing.ad.index.adunit;

import com.zing.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Zing
 * @date 2019-11-25
 */
@Slf4j
@Component
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {

    private static Map<Long, AdUnitObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    public Set<Long> match(Integer positionType) {
        Set<Long> unitIds = new HashSet<>();
        objectMap.forEach((k, v) -> {
            if (AdUnitObject.isAdSlotType(positionType, v.getPositionType())) {
                unitIds.add(k);
            }
        });

        return unitIds;
    }

    public List<AdUnitObject> fetch(Collection<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return Collections.emptyList();
        }

        List<AdUnitObject> result = new ArrayList<>();
        unitIds.forEach(u -> {
            AdUnitObject object = get(u);
            if (object == null) {
                log.error("AdUnitObject not found: {}", u);
                return;
            }
            result.add(object);
        });
        return result;
    }

    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdUnitObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, AdUnitObject value) {
        log.info("before update: {}", objectMap);
        AdUnitObject object = objectMap.get(key);
        if (object == null) {
            objectMap.put(key, value);
        } else {
            object.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, AdUnitObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}

