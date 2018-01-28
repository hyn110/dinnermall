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
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
@Api(description = "买家订单操作")
@RestController
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    IOrderService orderService;

    /**
     * 不在 @ApiOperation 返回对象,会导致 swagger2 调试界面的 Model 对象结构显示不出来
     *
     * @return
     */
    @ApiOperation(value = "只是用于获取表单对象,没有意义...")
    @PutMapping("/")
    public OrderForm orderForm() {
        return new OrderForm();
    }


    @ApiOperation(value = "创建订单")
    @ApiImplicitParam(name = "orderForm", value = "订单信息的实体对象", required = true, dataType = "OrderForm")
    @PostMapping("/create")
    public Object create(@Valid @ModelAttribute OrderForm orderForm, BindingResult bindingResult) {

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

        return ResultVO.builder()
                       .data(map)
                       .build();
    }


    @ApiOperation("分页获取用户订单列表")
    @GetMapping("/list")
    public Object list(@ApiParam(name = "openid", value = "用户微信openid", required = true)
//                       @RequestParam(value = "openid")
                               String openid,
                       @ApiParam(name = "page", value = "第几页")
                       @RequestParam(value = "page", defaultValue = "0")
                               Integer page,
                       @ApiParam(name = "size", value = "分页大小")
                       @RequestParam(value = "size", defaultValue = "10")
                               Integer size) {

        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单列表]微信openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest    pageable = new PageRequest(page, size, Sort.Direction.DESC, "createTime");
        Page<OrderDTO> result   = orderService.findByBuyerOpenid(openid, pageable);
        List<OrderDTO> list     = result.getContent();
        return ResultVO.builder()
                       .data(list)
                       .message("请求成功")
                       .build();
    }

    //订单详情
    @ApiOperation("获取用户指定订单的详情")
    @GetMapping("/detail")
    public Object detail(
            @ApiParam(name = "openid", value = "用户微信openid", required = true)
            @RequestParam("openid") String openid,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = orderService.findOne(openid, orderId);
        return ResultVO.builder()
                       .data(orderDTO)
                       .message("请求成功")
                       .build();
    }

    //取消订单
    @ApiOperation("取消订单")
    @PostMapping("/cancel")
    public Object cancel(
            @ApiParam(name = "openid", value = "用户微信openid", required = true)
            @RequestParam("openid") String openid,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam("orderId") String orderId) {
        orderService.cancel(openid, orderId);
        return ResultVO.builder()
                       .message("请求成功")
                       .build();
    }

}
