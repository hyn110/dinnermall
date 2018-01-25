package com.fmi110.dinnermall.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fmi110
 * @Description: 返回给前端的结果数据 , View Object
 * @Date 2018/1/25 17:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {
    /**结果码*/
    private int code;
    /**结果信息*/
    private String message;
    /**具体数据*/
    private T data;
}
