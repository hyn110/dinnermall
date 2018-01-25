package com.fmi110.dinnermall.repository;

import com.fmi110.dinnermall.domain.ProductInfo;
import com.fmi110.dinnermall.enums.ProductStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @author fmi110
 * @Description:
 * @Date 2018/1/25 18:45
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductInfoRepositoryTest {
    @Autowired
    ProductInfoRepository repository;

    @Test
    public void testSave() {
//        ProductInfo ca = ProductInfo.builder()
//                                    .categoryType(1)
//                                    .productDescription("皮蛋瘦肉粥")
//                                    .productName("皮蛋瘦肉粥")
//                                    .productIcon("http://localhost/xxx.jpg")
//                                    .productId("2")
//                                    .productPrice(new BigDecimal(4.8))
//                                    .productStatus(ProductStatusEnum.UP.getCode())
//                                    .productStock(20)
//                                    .build();
        ProductInfo ca = ProductInfo.builder()
                                    .categoryType(1)
                                    .productDescription("桂林米粉")
                                    .productName("桂林米粉")
                                    .productIcon("http://localhost/xxx.jpg")
                                    .productId("3")
                                    .productPrice(new BigDecimal(12))
                                    .productStatus(ProductStatusEnum.UP.getCode())
                                    .productStock(20)
                                    .build();
        repository.save(ca);
    }

}