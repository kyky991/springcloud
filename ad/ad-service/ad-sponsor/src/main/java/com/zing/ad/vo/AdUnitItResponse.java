package com.zing.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zing
 * @date 2019-11-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItResponse {

    private List<Long> ids;

}
