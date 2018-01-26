package com.fmi110.dinnermall.service.impl;

import com.fmi110.dinnermall.domain.OrderDetail;
import com.fmi110.dinnermall.dto.OrderDTO;
import com.fmi110.dinnermall.repository.OrderDetailRepository;
import com.fmi110.dinnermall.service.IOrderService;
import com.fmi110.dinnermall.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Test
    public void create() throws Exception {
//        OrderDTO orderDTO = new OrderDTO();
//        OrderDetail detail = new OrderDetail();
//        detail.setProductId("2");
////        detail.setProductId("22");
//        detail.setProductQuantity(30);
//        orderDTO.setBuyAddress("黑马");
//        orderDTO.setBuyerName("fmi110");
//        orderDTO.setBuyerOpenid("666");
//        orderDTO.setBuyerPhone("888888");
//        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
//        orderDetails.add(detail);
//        orderDTO.setOrderDetails(orderDetails);

        OrderDTO orderDTO = new OrderDTO();
        OrderDetail detail = new OrderDetail();
        detail.setProductId("3");
//        detail.setProductId("22");
        detail.setProductQuantity(1);
        orderDTO.setBuyAddress("传智");
        orderDTO.setBuyerName("fmi110");
        orderDTO.setBuyerOpenid("666");
        orderDTO.setBuyerPhone("1581152328");
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(detail);
        orderDTO.setOrderDetails(orderDetails);

        service.create(orderDTO);
    }

    @Test
    public void findAllOrdersByBuyerOpenId(){
        List<OrderDTO> list = service.findAllOrdersByBuyerOpenId("666");
//        list.forEach(e-> System.out.println(JsonUtils.toJson(e)));
        System.out.println(JsonUtils.toJson(list));
    }

    @Test
    public void cancel(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("5e93dffa79f4f773d6_1516931908000");
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderDTO.getOrderId());
        orderDTO.setOrderDetails(orderDetails);
        service.cancel(orderDTO);
    }

}