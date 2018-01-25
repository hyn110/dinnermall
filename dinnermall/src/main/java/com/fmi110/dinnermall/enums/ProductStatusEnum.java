package com.fmi110.dinnermall.enums;

import lombok.Getter;

/**
 * @author fmi110
 * @Description: 商品状态的枚举类
 * @Date 2018/1/25 17:00
 */
@Getter
public enum ProductStatusEnum {
    UP(0,"在架"), // 上架
    DOWN(1,"下架"); // 下架
    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ProductStatusEnum(Integer code){
        this.code=code;
    }
}
