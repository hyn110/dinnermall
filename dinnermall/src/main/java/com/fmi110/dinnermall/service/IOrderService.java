package com.fmi110.dinnermall.service;

import com.fmi110.dinnermall.domain.OrderMaster;
import com.fmi110.dinnermall.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author fmi110
 * @Description: 订单业务逻辑
 * @Date 2018/1/25 21:15
 */
public interface IOrderService {

    /**
     * 新建订单
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 支付订单
     */
    OrderDTO pay(OrderDTO orderDTO);
    /**
     * 取消订单 :
     * <p>1 判断订单状态</p>
     * <p>2 修改订单状态</p>
     * <p>3 已付款订单,执行退款逻辑</p>
     * <p>4 库存还原</p>
     *
     * @param orderDTO
     */
    OrderDTO cancel(OrderDTO orderDTO);
    /**完结订单*/
    OrderDTO finish(OrderDTO orderDTO);
    /**查询单个订单*/
    OrderDTO findOne(String orderId);

    /**
     * 查询指定用户的所有订单
     */
    List<OrderDTO> findAllOrdersByBuyerOpenId(String buyerOpenId);
    /**分页查询指定用户的所有订单,按创建时间降序*/
    Page<OrderDTO> findByBuyerOpenid(String buyerOpenId, Pageable pageable);
}
