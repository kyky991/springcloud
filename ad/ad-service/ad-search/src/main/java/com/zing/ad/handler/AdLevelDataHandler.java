package com.zing.ad.handler;

import com.zing.ad.dump.table.*;
import com.zing.ad.index.DataTable;
import com.zing.ad.index.IndexAware;
import com.zing.ad.index.adplan.AdPlanIndex;
import com.zing.ad.index.adplan.AdPlanObject;
import com.zing.ad.index.adunit.AdUnitIndex;
import com.zing.ad.index.adunit.AdUnitObject;
import com.zing.ad.index.creative.CreativeIndex;
import com.zing.ad.index.creative.CreativeObject;
import com.zing.ad.index.creativeunit.CreativeUnitIndex;
import com.zing.ad.index.creativeunit.CreativeUnitObject;
import com.zing.ad.index.keyword.UnitKeywordIndex;
import com.zing.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Slf4j
public class AdLevelDataHandler {

    public static void handleLevel2(AdPlanTable table, OpType type) {
        AdPlanObject object = new AdPlanObject(table.getId(), table.getUserId(), table.getPlanStatus(), table.getStartDate(), table.getEndDate());
        handlerBinlogEvent(DataTable.of(AdPlanIndex.class), object.getPlanId(), object, type);
    }

    public static void handleLevel2(CreativeTable table, OpType type) {
        CreativeObject object = new CreativeObject();
        BeanUtils.copyProperties(table, object);
        handlerBinlogEvent(DataTable.of(CreativeIndex.class), object.getAdId(), object, type);
    }

    public static void handleLevel3(AdUnitTable table, OpType type) {
        AdPlanObject planObject = DataTable.of(AdPlanIndex.class).get(table.getPlanId());
        if (planObject == null) {
            log.error("handleLevel3 error: {}", table.getPlanId());
            return;
        }

        AdUnitObject unitObject = new AdUnitObject();
        BeanUtils.copyProperties(table, unitObject);
        unitObject.setAdPlanObject(planObject);

        handlerBinlogEvent(DataTable.of(AdUnitIndex.class), unitObject.getUnitId(), unitObject, type);
    }

    public static void handleLevel3(CreativeUnitTable table, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(table.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(table.getAdId());
        if (unitObject == null || creativeObject == null) {
            log.error("handleLevel3 error: {}, {}", table.getUnitId(), table.getAdId());
            return;
        }

        CreativeUnitObject object = new CreativeUnitObject(table.getAdId(), table.getUnitId());
        handlerBinlogEvent(DataTable.of(CreativeUnitIndex.class), String.join("-", object.getAdId().toString(), object.getUnitId().toString()), object, type);
    }

    public static void handleLevel4(AdUnitKeywordTable table, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(table.getUnitId());
        if (unitObject == null) {
            log.error("handleLevel4 error: {}", table.getUnitId());
            return;
        }

        handlerBinlogEvent(DataTable.of(UnitKeywordIndex.class), table.getKeyword(), new HashSet<>(Collections.singleton(table.getUnitId())), type);
    }

    public static void handleLevel4(AdUnitItTable table, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(table.getUnitId());
        if (unitObject == null) {
            log.error("handleLevel4 error: {}", table.getUnitId());
            return;
        }

        handlerBinlogEvent(DataTable.of(UnitKeywordIndex.class), table.getItTag(), new HashSet<>(Collections.singleton(table.getUnitId())), type);
    }

    public static void handleLevel4(AdUnitDistrictTable table, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(table.getUnitId());
        if (unitObject == null) {
            log.error("handleLevel4 error: {}", table.getUnitId());
            return;
        }

        handlerBinlogEvent(DataTable.of(UnitKeywordIndex.class), String.join("-", table.getProvince(), table.getCity()), new HashSet<>(Collections.singleton(table.getUnitId())), type);
    }

    private static <K, V> void handlerBinlogEvent(IndexAware<K, V> index, K key, V value, OpType type) {
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }

}
