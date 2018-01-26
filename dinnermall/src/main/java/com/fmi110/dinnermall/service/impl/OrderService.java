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
import com.fmi110.dinnermall.utils.JsonUtils;
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

        return orderDTO;
    }

    /**
     * 支付订单
     *
     * @param orderDTO
     */
    @Transactional
    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        OrderMaster order = orderMasterRepository.findOne(orderDTO.getOrderId());
        if (null == order) {
            log.error("[取消订单]订单不存在,{}", JsonUtils.toJson(orderDTO));
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (!order.getPayStatus()
                 .equals(PayStatusEnum.NOT_PAY)) {
            log.error("[支付订单]订支付状态不对 : {}",JsonUtils.toJson(orderDTO));
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //TODO:支付订单,支付成功后修改状态
        order.setPayStatus(PayStatusEnum.PAYED.getStatus());
        BeanUtils.copyProperties(order,orderDTO);
        return orderDTO;
    }

    /**
     * 取消订单 :
     * <p>1 判断订单是否存在</p>
     * <p>2 判断订单状态:已取消,已完结的不可取消</p>
     * <p>3 修改订单状态为取消</p>
     * <p>4 库存还原</p>
     * <p>5 判断支付状态,如果已付款,执行退款逻辑</p>
     *
     * @param orderDTO
     */
    @Transactional
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        //1
        OrderMaster order = orderMasterRepository.findOne(orderDTO.getOrderId());
        if (null == order) {
            log.error("[取消订单]订单不存在,{}", JsonUtils.toJson(orderDTO));
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2
        if (order.getOrderStatus()
                 .equals(OrderStatusEnum.CANCEL) || order.getOrderStatus()
                                                         .equals(OrderStatusEnum.FINISHED)) {
            log.error("[取消订单]订单状态不正确,{}", JsonUtils.toJson(orderDTO));
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3
        order.setOrderStatus(OrderStatusEnum.CANCEL.getStatus());
        //4
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetails())) {
            log.error("[取消订单]没有订单明细,{}", JsonUtils.toJson(orderDTO));
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        // 修改库存是通过 cartDTO 实现的,所以这里需要把 orderDTO --> CartDTO
        List<CartDTO> list = orderDTO.getOrderDetails()
                                        .stream()
                                        .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                                        .collect(Collectors.toList());
        productService.increaseStock(list);

        //5
        if (PayStatusEnum.PAYED.getStatus()
                               .equals(order.getPayStatus())) {
            // TODO:执行退款
        }
        BeanUtils.copyProperties(order,orderDTO);
        return orderDTO;
    }

    /**
     * 完结订单
     * <p>1 判断订单状态,非取消订单能完结</p>
     * <p>2 修改订单状态</p>
     *
     * @param orderDTO
     */
    @Transactional
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster order = orderMasterRepository.findOne(orderDTO.getOrderId());
        if (null == order) {
            log.error("[取消订单]订单不存在,{}", JsonUtils.toJson(orderDTO));
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (order.getOrderStatus()
                 .equals(OrderStatusEnum.CANCEL)) {
            log.error("[订单完结]订单已取消,无法完结 ,订单信息:{}",JsonUtils.toJson(orderDTO));
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        order.setOrderStatus(OrderStatusEnum.FINISHED.getStatus());
        BeanUtils.copyProperties(order,orderDTO);
        return orderDTO;
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
