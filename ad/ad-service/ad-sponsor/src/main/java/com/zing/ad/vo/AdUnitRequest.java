package com.zing.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

    private Long planId;

    private String unitName;

    private Integer positionType;

    private Long budget;

    public boolean createValidate() {
        return planId != null && StringUtils.isNotEmpty(unitName) && positionType != null && budget != null;
    }

}
