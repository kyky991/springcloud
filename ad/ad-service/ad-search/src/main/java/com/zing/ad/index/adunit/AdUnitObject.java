package com.zing.ad.index.adunit;

import com.zing.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zing
 * @date 2019-11-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitObject {

    private Long unitId;
    private Long planId;
    private Integer unitStatus;
    private Integer positionType;

    private AdPlanObject adPlanObject;

    public void update(AdUnitObject newObject) {
        if (newObject.getPlanId() != null) {
            this.planId = newObject.getPlanId();
        }
        if (newObject.getPlanId() != null) {
            this.planId = newObject.getPlanId();
        }
        if (newObject.getUnitStatus() != null) {
            this.unitStatus = newObject.getUnitStatus();
        }
        if (newObject.getPositionType() != null) {
            this.positionType = newObject.getPositionType();
        }
        if (newObject.getAdPlanObject() != null) {
            this.adPlanObject = newObject.getAdPlanObject();
        }
    }

    public static boolean isKaiPing(int positionType) {
        return (positionType & AdUnitConstant.POSITION_TYPE.KAIPING) > 0;
    }

    public static boolean isTiePian(int positionType) {
        return (positionType & AdUnitConstant.POSITION_TYPE.TIEPIAN) > 0;
    }

    public static boolean isTiePianMiddle(int positionType) {
        return (positionType & AdUnitConstant.POSITION_TYPE.TIEPIAN_MIDDLE) > 0;
    }

    public static boolean isTiePianPause(int positionType) {
        return (positionType & AdUnitConstant.POSITION_TYPE.TIEPIAN_PAUSE) > 0;
    }

    public static boolean isTiePianPost(int positionType) {
        return (positionType & AdUnitConstant.POSITION_TYPE.TIEPIAN_POST) > 0;
    }

    public static boolean isAdSlotType(int adSlotType, int positionType) {
        switch (adSlotType) {
            case AdUnitConstant.POSITION_TYPE.KAIPING:
                return isKaiPing(positionType);
            case AdUnitConstant.POSITION_TYPE.TIEPIAN:
                return isTiePian(positionType);
            case AdUnitConstant.POSITION_TYPE.TIEPIAN_MIDDLE:
                return isTiePianMiddle(positionType);
            case AdUnitConstant.POSITION_TYPE.TIEPIAN_PAUSE:
                return isTiePianPause(positionType);
            case AdUnitConstant.POSITION_TYPE.TIEPIAN_POST:
                return isTiePianPost(positionType);
            default:
                return false;
        }
    }
}
