/**
 * 用户注册Resource
 */
package com.bjcre.resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjcre.bo.RegisterBo;
import com.bjcre.params.RegisterParam;
import com.bjcre.server.web.result.DataResult;

/**
 * @author 彩色照片
 *
 */
@Controller
@RequestMapping("users")
public class RegisterResource {
	
	@Autowired
    private RegisterBo registerBo;
	
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public DataResult add(
            @Validated RegisterParam param,
            BindingResult bindingResult, HttpServletRequest request) {
        DataResult result = new DataResult();
        result.setData(registerBo.add(param));
        return result;
    }
}
