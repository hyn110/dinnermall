package com.fmi110.dinnermall.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author fmi110
 * @Description: 类目信息(包含商品详情信息),用于装载数据返回给前端
 * @Date 2018/1/25 18:02
 */
@Data
public class CategoryVO {
    @JsonProperty("name")
    private String catoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfos;
}
