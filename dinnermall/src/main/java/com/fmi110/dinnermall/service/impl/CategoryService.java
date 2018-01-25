package com.fmi110.dinnermall.service.impl;

import com.fmi110.dinnermall.domain.ProductCategory;
import com.fmi110.dinnermall.repository.ProductCategoryRepository;
import com.fmi110.dinnermall.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author fmi110
 * @Description:
 * @Date 2018/1/25 16:21
 */
@Service
@Transactional
public class CategoryService implements ICategoryService {

    @Autowired
    ProductCategoryRepository repository;

    /**
     * 根据分类ID查询分类
     *
     * @param categoryId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public ProductCategory findOne(@NotNull Integer categoryId) {
        return repository.findOne(categoryId);
    }

    /**
     * 查询所有分类
     */
    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    /**
     * 根据类目编号查询
     *
     * @param catetoryTypes
     * @return
     */
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> catetoryTypes) {

        return repository.findByCategoryTypeIn(catetoryTypes);
    }

    /**
     * 新增(更新)类名
     *
     * @param category
     * @return
     */
    @Override
    public ProductCategory save(@NotNull ProductCategory category) {
        return repository.save(category);
    }
}
