package com.fmi110.dinnermall.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author fmi110
 * @Description: 商品分类表
 * @Date 2018/1/25 10:43
 */
@Builder
@NoArgsConstructor@AllArgsConstructor
@Data
@Entity
@Table(name = "product_category")
@DynamicUpdate
public class ProductCategory implements Serializable{
    @Id@Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自动增长
    private Integer categoryId;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "category_type")
    private Integer categoryType;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "update_time")
    private Timestamp updateTime;

}
