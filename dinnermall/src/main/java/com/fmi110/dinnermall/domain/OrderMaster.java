package com.fmi110.dinnermall.domain;

import com.fmi110.dinnermall.enums.OrderStatusEnum;
import com.fmi110.dinnermall.enums.PayStatusEnum;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author fmi110
 * @Description: 订单主表
 * @Date 2018/1/25 10:43
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_master")
@DynamicUpdate
public class OrderMaster implements Serializable {
    @Id
    @Column(name = "order_id")
    private String     orderId;
    @Column(name = "buyer_name")
    private String     buyerName;
    @Column(name = "buyer_phone")
    private String     buyerPhone;
    @Column(name = "buyer_address")
    private String     buyerAddress;
    @Column(name = "buyer_openid")
    private String     buyerOpenid;
    @Column(name = "order_amount")
    private BigDecimal orderAmount;
    @Column(name = "order_status")
    private Integer orderStatus = OrderStatusEnum.CREATED.getStatus();
    @Column(name = "pay_status")
    private Integer payStatus   = PayStatusEnum.NOT_PAY.getStatus();
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "update_time")
    private Timestamp updateTime;

}
