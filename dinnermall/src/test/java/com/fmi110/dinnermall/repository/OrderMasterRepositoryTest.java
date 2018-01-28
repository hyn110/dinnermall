package com.fmi110.dinnermall.repository;

import com.fmi110.dinnermall.domain.OrderMaster;
import com.fmi110.dinnermall.enums.OrderStatusEnum;
import com.fmi110.dinnermall.enums.PayStatusEnum;
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
 * @Date 2018/1/25 21:31
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderMasterRepositoryTest {
    @Autowired
    OrderMasterRepository repository;

    @Test
    public void save() throws Exception {
        OrderMaster order = OrderMaster.builder()
                                       .buyerAddress("幸福村")
                                       .buyerName("fmi110")
                                       .buyerOpenid("666")
                                       .buyerPhone("1584688888888")
                                       .orderId("1")
                                       .orderStatus(OrderStatusEnum.CREATED.getStatus())
                                       .payStatus(PayStatusEnum.NOT_PAY.getStatus())
                                       .orderAmount(new BigDecimal(12))
                                       .build();
        repository.save(order);
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        List<OrderMaster> list = repository.findByBuyerOpenidOrderByCreateTimeDesc("666");
        list.forEach(e-> System.out.println(e));
    }

}