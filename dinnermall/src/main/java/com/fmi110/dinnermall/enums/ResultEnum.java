package com.fmi110.dinnermall.enums;

import lombok.Getter;

/**
 * @author fmi110
 * @Description: 业务处理的结果状态枚举
 * @Date 2018/1/25 17:22
 */
@Getter
public enum ResultEnum {
    SUCCESS(200, "成功"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STATUS_SAME(11, "当前商品状态无需修改"),
    STOCK_NOT_ENOUGH(12, "商品库存不足"),

    ORDER_NOT_EXIST(21, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(22, "订单详情不存在"),
    ORDER_STATUS_ERROR(23, "订单状态不正确"), ORDER_PAY_STATUS_ERROR(31, "订单支付状态不对" );

    private Integer code;
    private String  message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
