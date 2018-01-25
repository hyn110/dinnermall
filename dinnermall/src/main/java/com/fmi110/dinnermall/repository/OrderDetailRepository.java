package com.fmi110.dinnermall.repository;

import com.fmi110.dinnermall.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author fmi110
 * @Description: OrderDetail 订单明细操作表
 * @Date 2018/1/25 11:39
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    /**
     * 根据订单号获取订单明细
     *
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
