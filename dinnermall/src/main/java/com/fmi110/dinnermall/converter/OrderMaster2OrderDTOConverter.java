package com.fmi110.dinnermall.converter;

import com.fmi110.dinnermall.domain.OrderMaster;
import com.fmi110.dinnermall.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fmi110
 * @Description: 订单对象转换到 订单dto对象
 * @Date 2018/1/26 10:14
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster origin) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(origin,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orders) {
        List<OrderDTO> list = orders.stream()
                                       .map(e -> convert(e))
                                       .collect(Collectors.toList());
        return list;
    }
}
