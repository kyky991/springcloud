package com.zing.cloud.product.service;

import com.zing.cloud.product.common.DecreaseStockInput;
import com.zing.cloud.product.common.ProductInfoOutput;
import com.zing.cloud.product.dataobject.ProductInfo;

import java.util.List;

public interface IProductService {

    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     *
     * @param productIdList
     * @return
     */
    List<ProductInfoOutput> findList(List<String> productIdList);

    /**
     * 扣库存
     *
     * @param decreaseStockInputList
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
