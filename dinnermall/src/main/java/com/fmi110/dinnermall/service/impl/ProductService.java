package com.fmi110.dinnermall.service.impl;

import com.fmi110.dinnermall.domain.ProductInfo;
import com.fmi110.dinnermall.dto.CartDTO;
import com.fmi110.dinnermall.enums.ProductStatusEnum;
import com.fmi110.dinnermall.enums.ResultEnum;
import com.fmi110.dinnermall.exception.SellException;
import com.fmi110.dinnermall.repository.ProductInfoRepository;
import com.fmi110.dinnermall.service.IProductService;
import com.fmi110.dinnermall.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author fmi110
 * @Description:
 * @Date 2018/1/25 16:50
 */
@Service
@Transactional(readOnly = true)
@Slf4j
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

    /**
     * 根据商品Id查询商品
     *
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    /**
     * 扣指定商品的库存
     *
     * @param cartDTOS
     */
    @Transactional
    @Override
    public void decreaseStock(List<CartDTO> cartDTOS) {
        // 遍历扣库存
        cartDTOS.forEach(dto->{
            // 1 确定商品存在
            // 2 判断库存是否充足
            // 3 扣库存
            ProductInfo productInfo = repository.findOne(dto.getProductId());
            if (null == productInfo) {
                log.error("[扣库存]商品不存在 : {}", JsonUtils.toJson(dto));
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int stock = productInfo.getProductStock() - dto.getProductQuantity();
            if (stock < 0) {
                log.error("[扣库存]库存不足,库存 :{},需扣除{}",productInfo.getProductStock() , dto.getProductQuantity());
                throw new SellException(ResultEnum.STOCK_NOT_ENOUGH);
            }
            productInfo.setProductStock(stock);
            repository.save(productInfo);
        });
    }

    /**
     * 增加库存
     * <p>1 确定商品存在</p>
     * <p>2 修改库存</p>
     * @param cartDTOS
     */
    @Override
    public void increaseStock(List<CartDTO> cartDTOS) {
        cartDTOS.forEach((CartDTO c) ->{
            ProductInfo product = repository.findOne(c.getProductId());
            if (product == null) {
                log.error("[加库存]商品不存在 : {}", JsonUtils.toJson(c));
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            product.setProductStock(product.getProductStock()+c.getProductQuantity());
            repository.save(product);
        });
    }

    public static void main(String[] arg){
        String[] array = {"a", "b", "c"};
        List<String> list = Arrays.asList(array);
        list.forEach(e->{
            String a = e + "===";
            System.out.println(a);
            throw new RuntimeException("抛异常");
        });
    }
}
