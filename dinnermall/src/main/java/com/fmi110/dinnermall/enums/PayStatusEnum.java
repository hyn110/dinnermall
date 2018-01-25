package com.fmi110.dinnermall.enums;

import lombok.Getter;

/**
 * @author fmi110
 * @Description: 支付状态枚举
 * @Date 2018/1/25 21:25
 */
@Getter
public enum PayStatusEnum {
    NOT_PAY(0, "待支付"),
    PAYED(1, "已支付"),
    NOT_REFUND(2, "等待退款"),
    REFUND(3, "退款成功");
    private Integer status;
    private String  message;

    PayStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
