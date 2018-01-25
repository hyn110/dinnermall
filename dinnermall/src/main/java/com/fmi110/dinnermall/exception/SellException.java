package com.fmi110.dinnermall.exception;

import com.fmi110.dinnermall.enums.ResultEnum;

/**
 * @author fmi110
 * @Description: 自定义的异常类
 * @Date 2018/1/25 17:18
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException( ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public SellException( Integer code,String message) {
        super(message);
        this.code = code;
    }
}
