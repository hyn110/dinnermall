package com.fmi110.dinnermall.service;

import com.fmi110.dinnermall.domain.ProductCategory;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author fmi110
 * @Description: 商品分类业务层
 * @Date 2018/1/25 16:13
 */
public interface ICategoryService {
    /**
     * 根据分类ID查询分类
     * @param categoryId
     * @return
     */
    ProductCategory findOne(@NotNull Integer categoryId);

    /**
     * 查询所有分类
     */
    List<ProductCategory> findAll();

    /**
     * 根据类目编号查询
     * @param catetoryTypes
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> catetoryTypes);

    /**
     * 新增(更新)类名
     * @param category
     * @return
     */
    ProductCategory save(@NotNull ProductCategory category);

}
