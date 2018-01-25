package com.fmi110.dinnermall.service;

import com.fmi110.dinnermall.domain.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author fmi110
 * @Description:
 * @Date 2018/1/25 16:48
 */
public interface IProductService {
    /**
     * 根据商品状态查询
     * @param status
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer status);

    /**
     * 保存(更新)商品
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 分页查找所有商品
     * @param pageable
     * @return
     */
    public Page<ProductInfo> findProducts(Pageable pageable);
    /**
     * 分页查询所有上架商品
     * @return
     */
    List<ProductInfo> findProductsOnSale(Integer page, Integer size);

    /**
     * 查询所有上架商品的总数
     * @return
     */
    Integer getTotalOnSale();

    /**
     * 上架指定商品
     * @param productId
     * @return
     */
    ProductInfo onSale(String productId);

    /**
     * 下架指定商品
     * @param productId
     * @return
     */
    ProductInfo offSale(String productId);

    /**
     * 加库存
     */

    /**
     * 减库存
     */
}
