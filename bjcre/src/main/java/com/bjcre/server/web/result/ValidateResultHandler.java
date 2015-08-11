package com.bjcre.server.web.result;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.bjcre.server.web.exception.BaseBizException;
import com.bjcre.server.web.exception.BaseReturnStatus;

/**
 * 类ParamsValidator.java的实现描述：参数验证结果生成
 *
 * @author lixiaoyong 14-7-17 下午4:39
 */
public class ValidateResultHandler {

	public static void doCheck(BindingResult bindingResult)
            throws BaseBizException {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            StringBuilder errorSB = new StringBuilder();
            for (FieldError fieldError : fieldErrorList) {
                // errorSB.append("[").append(fieldError.getField()).append(":")
                // .append(fieldError.getDefaultMessage()).append("]");
                errorSB.append("[").append(fieldError.getDefaultMessage())
                        .append("]");
            }
            throw new BaseBizException(BaseReturnStatus.PARAMETER_ERROR,
                    errorSB.toString());
        }
    }
}
