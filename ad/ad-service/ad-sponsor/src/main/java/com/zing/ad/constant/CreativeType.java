package com.zing.ad.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Getter
@AllArgsConstructor
public enum CreativeType {

    /**
     * 创意类型
     */
    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TEXT(3, "文本"),

    ;

    private Integer type;

    private String desc;

}
