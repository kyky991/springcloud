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
public class AdPlanRequest {

    private Long id;

    private Long userId;

    private String planName;

    private String startDate;

    private String endDate;

    public boolean createValidate() {
        return userId != null && StringUtils.isNotEmpty(planName) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate);
    }

    public boolean updateValidate() {
        return id != null && userId != null;
    }

    public boolean deleteValidate() {
        return id != null && userId != null;
    }

}
