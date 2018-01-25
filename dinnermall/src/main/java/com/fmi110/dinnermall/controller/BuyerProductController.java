package com.fmi110.dinnermall.controller;

import com.fmi110.dinnermall.VO.CategoryVO;
import com.fmi110.dinnermall.VO.ProductInfoVO;
import com.fmi110.dinnermall.VO.ResultVO;
import com.fmi110.dinnermall.domain.ProductCategory;
import com.fmi110.dinnermall.domain.ProductInfo;
import com.fmi110.dinnermall.enums.ResultEnum;
import com.fmi110.dinnermall.service.ICategoryService;
import com.fmi110.dinnermall.service.IProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fmi110
 * @Description: 商品显示模块
 * @Date 2018/1/25 18:27
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    IProductService  productService;
    @Autowired
    ICategoryService categoryService;

    @ApiOperation("获取商品列表,按照榜单分类")
    @GetMapping("/list")
    public ResultVO list() {
        // 1 查询所有上架商品
        List<ProductInfo> allProductsOnSale = productService.findAllProductsOnSale();

        // 2 从商品中提取类名的 类目type
        List<Integer> categoryTypeList = allProductsOnSale.stream()
                                                          .map(e -> e.getCategoryType())
                                                          .distinct()
                                                          .collect(Collectors.toList());
        // 3 获取已上架商品对应的所有类名
        /**
         * 用到了 select * from tableName in (xx,xx,xx) 语法,减少数据库的交互次数
         */
        List<ProductCategory> categoryTypes = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 4 开始封装返回前端的展示数据 : 对商品进行分类
        List<CategoryVO> categoryVOList = new ArrayList<>();
        for (ProductCategory categoryType : categoryTypes) {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(categoryType, categoryVO);
            // 遍历所有的商品,进行分类
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : allProductsOnSale) {
                if (productInfo.getCategoryType() == categoryType.getCategoryType()) {

                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            categoryVO.setProductInfos(productInfoVOList);
            categoryVOList.add(categoryVO);
        }

        return ResultVO.builder()
                       .code(ResultEnum.SUCCESS.getCode())
                       .message(ResultEnum.SUCCESS.getMessage())
                       .data(categoryVOList)
                       .build();

    }
}
