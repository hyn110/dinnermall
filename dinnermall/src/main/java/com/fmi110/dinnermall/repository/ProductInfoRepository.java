package com.fmi110.dinnermall.repository;

import com.fmi110.dinnermall.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author fmi110
 * @Description: 商品信息操作
 * @Date 2018/1/25 11:44
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    /**
     * 根据商品上架状态查询
     * @param status
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer status);

    /**
     * 查询所有上架商品
     * @param status
     * @return
     */
    List<ProductInfo> findAllByProductStatus(Integer status);

    /**
     * 分页查找所有在架商品
     * @param page
     * @param size
     * @return
     */
    @Query(value = "select * from product_info where product_status=0 LIMIT ?1,?2",nativeQuery = true)
    List<ProductInfo> findAllOnSale(int page, int size);

    /**
     * 查询所有在架商品总数
     * @return
     */
    @Query(value="select count(*) from product_info where product_status=0",nativeQuery = true)
    Integer getTotalOnSale();

    /**
     * 扣库存
     */
    @Modifying
    @Query(value = "UPDATE product_info SET product_stock=product_stock-?2 WHERE product_id=?1 AND product_stock>=?2",nativeQuery = true)
    Integer decreaseStock(String productId, Integer count);

}
