package com.fmi110.dinnermall.repository;

import com.fmi110.dinnermall.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author fmi110
 * @Description: 商品分类操作
 * @Date 2018/1/25 11:43
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> catetoryTypes);
}
