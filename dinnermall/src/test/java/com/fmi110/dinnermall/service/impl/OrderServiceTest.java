package com.fmi110.dinnermall.service.impl;

import com.fmi110.dinnermall.domain.OrderDetail;
import com.fmi110.dinnermall.dto.OrderDTO;
import com.fmi110.dinnermall.service.IOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

/**
 * @author fmi110
 * @Description:
 * @Date 2018/1/26 9:51
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest {
    @Autowired
    IOrderService service;
    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        OrderDetail detail = new OrderDetail();
        detail.setDetailId("11");
        detail.setProductId("2");
//        detail.setProductId("22");
        detail.setProductQuantity(30);
        orderDTO.setBuyAddress("黑马");
        orderDTO.setBuyerName("fmi110");
        orderDTO.setBuyerOpenid("666");
        orderDTO.setBuyerPhone("888888");
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(detail);
        orderDTO.setOrderDetails(orderDetails);

        service.create(orderDTO);
    }

}