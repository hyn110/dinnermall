package com.fmi110.dinnermall.repository;

import com.fmi110.dinnermall.domain.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fmi110
 * @Description: 卖家信息操作
 * @Date 2018/1/25 11:44
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {
}
