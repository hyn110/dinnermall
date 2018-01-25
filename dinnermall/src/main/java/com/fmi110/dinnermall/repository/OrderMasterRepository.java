package com.fmi110.dinnermall.repository;

import com.fmi110.dinnermall.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fmi110
 * @Description: 订单主表操作明细
 * @Date 2018/1/25 11:41
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    /**
     * 根据用户微信号 openID 查询订单 , 按订单创建时间降序排列
     * @param buyerOpenId
     * @param pageable 分页信息
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);
}
