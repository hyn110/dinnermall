package com.fmi110.dinnermall.service.impl;

import com.fmi110.dinnermall.domain.ProductInfo;
import com.fmi110.dinnermall.enums.ProductStatusEnum;
import com.fmi110.dinnermall.enums.ResultEnum;
import com.fmi110.dinnermall.exception.SellException;
import com.fmi110.dinnermall.repository.ProductInfoRepository;
import com.fmi110.dinnermall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fmi110
 * @Description:
 * @Date 2018/1/25 16:50
 */
@Service
@Transactional(readOnly = true)
public class ProductService implements IProductService {

    @Autowired
    ProductInfoRepository repository;
    /**
     * 根据商品状态查询
     *
     * @param status
     * @return
     */
    @Override
    public List<ProductInfo> findByProductStatus(Integer status) {
        return repository.findByProductStatus(status);
    }

    /**
     * 保存
     *
     * @param productInfo
     * @return
     */
    @Transactional
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    /**
     * 分页查询所有上架商品
     */
    @Override
    public List<ProductInfo> findProductsOnSale(Integer page, Integer size) {
        return repository.findAllOnSale(page,size);
    }

    /**
     * 查询所有上架商品的总数
     *
     * @return
     */
    @Override
    public Integer getTotalOnSale() {
        return repository.getTotalOnSale();
    }


    @Override
    public Page<ProductInfo> findProducts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 上架指定商品
     *
     * @param productId
     * @return
     */
    @Transactional
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (ProductStatusEnum.UP.getCode() == productInfo.getProductStatus()) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_SAME);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    /**
     * 下架指定商品
     *
     * @param productId
     * @return
     */
    @Transactional
    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (ProductStatusEnum.UP.getCode() == productInfo.getProductStatus()) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_SAME);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }

    /**
     * 查询所有上架的商品
     *
     * @return
     */
    @Override
    public List<ProductInfo> findAllProductsOnSale() {
        return repository.findAllByProductStatus(ProductStatusEnum.UP.getCode());
    }
}
