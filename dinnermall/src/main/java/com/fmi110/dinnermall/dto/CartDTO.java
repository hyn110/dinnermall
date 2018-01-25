package com.fmi110.dinnermall.dto;

import lombok.Data;

/**
 * @author fmi110
 * @Description: 商品以及对应的数量
 * @Date 2018/1/25 22:40
 */
@Data
public class CartDTO {
    /**
     * 商品Id.
     */
    private String productId;

    /**
     * 数量.
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
