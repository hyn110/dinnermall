package com.fmi110.dinnermall.repository;

import com.fmi110.dinnermall.domain.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author fmi110
 * @Description:
 * @Date 2018/1/25 18:45
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductCategoryRepositoryTest {
    @Autowired
    ProductCategoryRepository repository;

    @Test
    public void testSave() {
        ProductCategory ca = ProductCategory.builder()
                                             .categoryId(1)
                                             .categoryName("热销榜")
                                             .categoryType(1)
                                             .build();
        repository.save(ca);
    }

}