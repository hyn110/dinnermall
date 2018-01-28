package com.fmi110.dinnermall.formbean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 接收订单信息的表单
 */
@Data
@ApiModel(description = "对应订单表单")
public class OrderForm {

    /**
     * 买家姓名
     */
    @ApiModelProperty(value = "买家姓名",required = true)
    @NotEmpty(message = "姓名必填")
    private String name;

    /**
     * 买家手机号
     */
    @ApiModelProperty(value = "买家手机号",required = true)
    @NotEmpty(message = "手机号必填")
    private String phone;

    /**
     * 买家地址
     */
    @ApiModelProperty(value = "买家地址",required = true)
    @NotEmpty(message = "地址必填")
    private String address;

    /**
     * 买家微信openid
     */
    @ApiModelProperty(value = "买家微信openid",required = true)
    @NotEmpty(message = "openid必填")
    private String openid;

    /**
     * 购物车 , 前端需要把购物车数据转换为 json 字符串传到后台 --> orderDetails
     */
    @ApiModelProperty(value = "订单详情",required = true,example = " [{'productId':2,'productQuantity':3}]")
    @NotEmpty(message = "购物车不能为空")
    private String items;


}
