package com.fmi110.dinnermall.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author fmi110
 * @Description: 商品信息表
 * @Date 2018/1/25 10:43
 */
@Data
@Entity
@Table(name = "product_info")
@DynamicUpdate
public class ProductInfo {
    @Id@Column(name = "product_id")
    private String productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_price")
    private BigDecimal productPrice;
    @Column(name = "product_stock")
    private int productStock;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "product_icon")
    private String productIcon;
    @Column(name = "category_type")
    private int categoryType;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "update_time")
    private Timestamp updateTime;

}
