package com.fmi110.dinnermall.enums;

import lombok.Getter;

/**
 * @author fmi110
 * @Description: 业务处理的结果状态枚举
 * @Date 2018/1/25 17:22
 */
@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STATUS_SAME(11,"当前商品状态无需修改")

    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
