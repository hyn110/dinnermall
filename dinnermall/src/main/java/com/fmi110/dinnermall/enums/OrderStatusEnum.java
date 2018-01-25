package com.fmi110.dinnermall.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author fmi110
 * @Description: 订单状态枚举
 * @Date 2018/1/25 21:18
 */
@Getter
public enum OrderStatusEnum {
    CREATED(0,"订单新建,等待卖家确认"),
    CHECKED(1,"卖家接单"),
    SHIPPING(2,"配送途中"),
    FINISHED(3,"订单完成"),
    CANCEL(4,"订单取消")
    ;
    private Integer status;
    private String message;

    OrderStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
