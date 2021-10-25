package com.fh.store.service;


import com.fh.store.entity.Product;

import java.util.List;

public interface IProductService {
    /**
     * 查找热榜前4名
     * @return
     */
    List<Product> findHotList();

    /**
     * 根据商品id查询商品详情
     * @param id 商品id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */
    Product findById(Integer id);

    /**
     * 查找新品前4名
     * @return
     */
    List<Product> findNewList();
}
