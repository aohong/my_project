package com.bjcre.resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjcre.params.QueryParam;
import com.bjcre.server.web.result.BaseResult;
import com.bjcre.server.web.result.DataResult;

/**
 * 结算系统Server
 * 
 * @author zhangyiming8 2014-12-16 19:40
 */
@Controller("test")
public class TestResouce {
    // private final static Logger logger =
    // Logger.getLogger(SettleServer.class);

    @RequestMapping(value = "method1", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult querySettlePeriodListByParams(
            @Validated QueryParam params,
            BindingResult bindingResult, HttpServletRequest request) {

        DataResult result = new DataResult();
        return result;
    }

    @RequestMapping(value = "method1", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postMethod(
            @Validated QueryParam params,
            BindingResult bindingResult, HttpServletRequest request) {
System.out.println("params.getParam()="+params.getParam());
        DataResult result = new DataResult();
        return result;
    }

	@RequestMapping(value = "errorMethod", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult errorMethod(@Validated QueryParam params,
            BindingResult bindingResult, HttpServletRequest request) {

        DataResult result = new DataResult();
        return result;
    }

    @RequestMapping(value = "method1/{param:\\d+}{param1}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult param(@PathVariable String param,
            @PathVariable String param1,
            HttpServletRequest request) {
        System.out.println(param);
        System.out.println(param1);
        DataResult result = new DataResult();
        return result;
    }
}

