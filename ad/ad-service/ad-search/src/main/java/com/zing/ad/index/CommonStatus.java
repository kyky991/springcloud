package com.zing.ad.index;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Getter
@AllArgsConstructor
public enum CommonStatus {

    /**
     * 状态
     */
    VALID(1, "有效"),
    INVALID(0, "无效"),

    ;

    private Integer status;

    private String desc;

}
