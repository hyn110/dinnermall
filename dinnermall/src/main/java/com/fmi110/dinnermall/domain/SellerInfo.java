package com.fmi110.dinnermall.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author fmi110
 * @Description: 卖家信息表
 * @Date 2018/1/25 11:48
 */
@Builder
@NoArgsConstructor@AllArgsConstructor
@Data
@Entity
@Table(name = "seller_info")
@DynamicUpdate
public class SellerInfo implements Serializable{
    @Id@Column(name = "id")
    private String id;
    @Basic@Column(name = "username")
    private String username;
    @Basic@Column(name = "password")
    private String password;
    @Basic@Column(name = "openid")
    private String openid;
    @Basic@Column(name = "create_time")
    private Timestamp createTime;
    @Basic@Column(name = "update_time")
    private Timestamp updateTime;

}
