package com.zing.ad.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Getter
@AllArgsConstructor
public enum CreativeMaterialType {

    /**
     * 创意物料类型
     */
    JPG(1, "jpg"),
    BMP(2, "bmp"),

    MP4(3, "mp4"),
    AVI(4, "avi"),

    TXT(5, "txt"),

    ;

    private Integer type;

    private String desc;

}
