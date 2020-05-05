package com.zing.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    private Float latitude;
    private Float longitude;

    private String city;
    private String province;

}
