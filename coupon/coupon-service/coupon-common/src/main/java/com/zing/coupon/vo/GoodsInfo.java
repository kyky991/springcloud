package com.zing.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品信息
 *
 * @author Zing
 * @date 2020-05-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfo {

    /**
     * 商品类型
     */
    private Integer type;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品数量
     */
    private Integer count;

}
