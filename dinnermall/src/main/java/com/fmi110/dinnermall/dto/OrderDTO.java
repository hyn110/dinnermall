package com.fmi110.dinnermall.dto;

import com.fmi110.dinnermall.domain.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author fmi110
 * @Description: 订单信息对象  data translate object
 * @Date 2018/1/25 21:57
 */
@Data
public class OrderDTO {
    private String            orderId;
    private String            buyerName;
    private String            buyerPhone;
    private String            buyAddress;
    private String            buyerOpenid;
    private BigDecimal        orderAmount;
    private Integer           orderStatus;
    private Integer           payStatus;
    private Timestamp         createTime;
    private Timestamp         updateTime;
    private List<OrderDetail> orderDetails;
}
