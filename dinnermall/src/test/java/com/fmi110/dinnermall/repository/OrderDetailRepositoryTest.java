package com.fmi110.dinnermall.repository;

import com.fmi110.dinnermall.domain.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fmi110
 * @Description:
 * @Date 2018/1/25 12:04
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository repository;

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> list = repository.findByOrderId("1");
        System.out.println(list);
    }

    @Test
    public void testSave() {
        OrderDetail o = OrderDetail.builder()
                                   .detailId("1")
                                   .productId("mian")
                                   .productPrice(new BigDecimal(12.00))
                                   .orderId("1")
                                   .productName("湖南大碗面")
                                   .build();
        repository.save(o);
    }
}