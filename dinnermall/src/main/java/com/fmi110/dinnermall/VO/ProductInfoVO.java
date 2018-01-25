package com.fmi110.dinnermall.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author fmi110
 * @Description: 商品详情信息
 * @Date 2018/1/25 18:20
 */
@Data
public class ProductInfoVO {
    @JsonProperty("id")
    private String     productId;
    @JsonProperty("name")
    private String     productName;
    @JsonProperty("price")
    private BigDecimal productPrice;
    @JsonProperty("description")
    private String     productDescription;
    @JsonProperty("icon")
    private String     productIcon;
}
