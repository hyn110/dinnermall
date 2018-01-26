package com.fmi110.dinnermall.service.impl;

import com.fmi110.dinnermall.converter.OrderMaster2OrderDTOConverter;
import com.fmi110.dinnermall.domain.OrderDetail;
import com.fmi110.dinnermall.domain.OrderMaster;
import com.fmi110.dinnermall.domain.ProductInfo;
import com.fmi110.dinnermall.dto.CartDTO;
import com.fmi110.dinnermall.dto.OrderDTO;
import com.fmi110.dinnermall.enums.OrderStatusEnum;
import com.fmi110.dinnermall.enums.PayStatusEnum;
import com.fmi110.dinnermall.enums.ResultEnum;
import com.fmi110.dinnermall.exception.SellException;
import com.fmi110.dinnermall.repository.OrderDetailRepository;
import com.fmi110.dinnermall.repository.OrderMasterRepository;
import com.fmi110.dinnermall.repository.ProductInfoRepository;
import com.fmi110.dinnermall.service.IOrderService;
import com.fmi110.dinnermall.service.IProductService;
import com.fmi110.dinnermall.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author fmi110
 * @Description: 订单操作逻辑
 * @Date 2018/1/25 22:05
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class OrderService implements IOrderService {

    @Autowired
    ProductInfoRepository productInfoRepository;
    @Autowired
    IProductService       productService;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderMasterRepository orderMasterRepository;

    /**
     * 新建订单
     *
     * @param orderDTO
     */
    @Transactional
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        // 1 查询商品价格,库存
        // 2 计算总价
        // 3 写入订单数据库:订单详情表,订单表
        // 4 扣库存
        BigDecimal orderAmount = new BigDecimal(0);
        String     orderId     = KeyUtils.getUniqueKey();

        List<OrderDetail> orderDetails = orderDTO.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            ProductInfo productInfo = productInfoRepository.findOne(orderDetail.getProductId());
            if (null == productInfo) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 计算总价
            orderAmount = productInfo.getProductPrice()
                                     .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                                     .add(orderAmount);


            BeanUtils.copyProperties(productInfo, orderDetail); // 复制商品的相关信息
            // 设置订单 id 和 订单详情的id
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtils.getUniqueKey());
            orderDetailRepository.save(orderDetail); // 3
        }

        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster); // 先拷贝,防止id amount被覆盖
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(PayStatusEnum.NOT_PAY.getStatus());
        orderMaster.setOrderStatus(OrderStatusEnum.CREATED.getStatus()); // 3
        orderMasterRepository.save(orderMaster);

        List<CartDTO> cartDTOS = orderDetails.stream()
                                             .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                                             .collect(Collectors.toList());
        productService.decreaseStock(cartDTOS);

        return null;
    }

    /**
     * 支付订单
     *
     * @param orderDTO
     */
    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        return null;
    }

    /**
     * 取消订单
     *
     * @param orderDTO
     */
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    /**
     * 完结订单
     *
     * @param orderDTO
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    /**
     * 查询单个订单
     *
     * @param orderId
     */
    @Override
    public OrderDTO findOne(String orderId) {
        // 1 查询主订单
        // 2 根据订单id , 查询订单详情
        OrderMaster order = orderMasterRepository.findOne(orderId);
        if (null == order) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getOrderId());
        if (CollectionUtils.isEmpty(details)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order,orderDTO);
        orderDTO.setOrderDetails(details);
        return orderDTO;
    }

    /**
     * 查询指定用户的所有订单 , 这里暂时抓取订单详情信息
     * 可以不做空判断,查不到数据,就说明客户没下订单
     * @param buyerOpenId
     */
    @Override
    public List<OrderDTO> findAllOrdersByBuyerOpenId(String buyerOpenId) {
        List<OrderMaster> orders =
                orderMasterRepository.findByBuyerOpenidOrderByCreateTimeDesc(buyerOpenId);
        return OrderMaster2OrderDTOConverter.convert(orders);
    }

    /**
     * 分页查询指定用户的所有订单,按创建时间降序
     *
     * @param buyerOpenId
     * @param pageable
     */
    @Override
    public Page<OrderDTO> findByBuyerOpenid(String buyerOpenId, Pageable pageable) {

        Page<OrderMaster> orders = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);


        List<OrderDTO> list = OrderMaster2OrderDTOConverter.convert(orders.getContent());

        return new PageImpl<OrderDTO>(list,pageable,orders.getTotalElements());

    }
}
