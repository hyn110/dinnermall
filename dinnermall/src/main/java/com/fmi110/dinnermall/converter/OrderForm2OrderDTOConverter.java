package com.fmi110.dinnermall.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fmi110.dinnermall.domain.OrderDetail;
import com.fmi110.dinnermall.dto.OrderDTO;
import com.fmi110.dinnermall.enums.ResultEnum;
import com.fmi110.dinnermall.exception.SellException;
import com.fmi110.dinnermall.formbean.OrderForm;
import com.fmi110.dinnermall.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * orderForm 对象转化为 orderDTO 对象
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = JsonUtils.parse(orderForm.getItems(), new TypeReference<ArrayList<OrderDetail>>() {});
        } catch (Exception e) {
            log.error("[对象转换]错误, string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetails(orderDetailList);

        return orderDTO;
    }

    public static void main(String[] args) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("111");
        orderDTO.setBuyerPhone("222");
        orderDTO.setBuyerAddress("333");
        orderDTO.setBuyerOpenid("666");

        ArrayList<OrderDTO> list = new ArrayList<>();
        list.add(orderDTO);

        String s = JsonUtils.toJson(list,true);
        System.out.println(s);

        ArrayList parse = JsonUtils.parse(s, ArrayList.class);
        ArrayList<OrderDTO>    parse1 = JsonUtils.parse(s, new TypeReference<ArrayList<OrderDTO>>() {});
        System.out.println(parse1);

        OrderDetail detail = new OrderDetail();
        System.out.println(JsonUtils.toJson(detail,true));

//        {"productId":null,"productQuantity":0}
    }
}
