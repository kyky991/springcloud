package com.zing.ad.index;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Getter
@AllArgsConstructor
public enum DataLevel {

    LEVEL2("2", "level 2"),
    LEVEL3("3", "level 3"),
    LEVEL4("4", "level 4"),

    ;

    private String level;
    private String desc;

}
