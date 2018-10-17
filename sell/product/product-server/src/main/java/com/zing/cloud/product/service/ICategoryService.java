package com.zing.cloud.product.service;

import com.zing.cloud.product.dataobject.ProductCategory;

import java.util.List;

public interface ICategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
