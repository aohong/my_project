/*
 * Copyright 2012-2014 Wanda.cn All right reserved. This software is the
 * confidential and proprietary information of Wanda.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Wanda.cn.
 */
package com.bjcre.server.web.exception;


/**
 * 类TradeException.java的实现描述：交易异常类
 * 
 * @author wangyibo 2013-8-19 上午10:22:26
 */
public class BaseBizException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 5690451309757589228L;
    /**
     * 错误码
     */
    private final BaseReturnStatus returnStatusEnum;
    /**
     * 错误信息
     */
    private String message;

    public BaseBizException(BaseReturnStatus returnStatusEnum, Object... params) {
        this.returnStatusEnum = returnStatusEnum;
		if (params == null || params.length == 0) {
            this.message = returnStatusEnum.getDesc();
        } else {
            this.message = String.format(returnStatusEnum.getDesc(), params);
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getResultCode() {
        return returnStatusEnum.getValue();
    }
}
