package com.fmi110.dinnermall.controller;

import com.fmi110.dinnermall.VO.ResultVO;
import com.fmi110.dinnermall.converter.OrderForm2OrderDTOConverter;
import com.fmi110.dinnermall.dto.OrderDTO;
import com.fmi110.dinnermall.enums.ResultEnum;
import com.fmi110.dinnermall.exception.SellException;
import com.fmi110.dinnermall.formbean.OrderForm;
import com.fmi110.dinnermall.service.IOrderService;
import com.fmi110.dinnermall.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fmi110
 * @Description: 订单
 * <p>创建订单</p>
 * <p>查看订单列表</p>
 * <p>取消订单</p>
 * <p>订单详情</p>
 * @Date 2018/1/28 10:04
 */
@Api(description = "用户订单操作相关")
@RestController
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    IOrderService orderService;

    /**
     * 不在 @ApiOperation 返回对象,会导致 swagger2 调试界面的 Model 对象结构显示不出来
     * @return
     */
    @ApiOperation(value = "只是用于获取表单对象,没有意义...")
    @PutMapping ("/")
    public OrderForm orderForm(){
        return new OrderForm();
    }


    @ApiOperation(value = "创建订单")
    @ApiImplicitParam(name = "orderForm",value = "订单信息的实体对象",required = true,dataType = "OrderForm")
    @PostMapping("/create")
    public Object create(@Valid@ModelAttribute OrderForm orderForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.error("[创建订单]参数错误,orderFrom = {}", JsonUtils.toJson(orderForm));
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        // orderForm --> OrderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetails())) {
            log.error("[创建订单]购物车为空,orderFrom = {}", JsonUtils.toJson(orderForm));
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO result = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());

        return ResultVO.builder().data(map).build();
    }
}
