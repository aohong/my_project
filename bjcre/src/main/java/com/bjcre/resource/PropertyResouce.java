package com.bjcre.resource;

import com.bjcre.bo.HouseInfoBo;
import com.bjcre.params.HouseAddParam;
import com.bjcre.params.HouseQueryParam;
import com.bjcre.server.web.result.CountDataResult;
import com.bjcre.server.web.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 配置资源类
 * 
 * @author aohong
 */
@Controller
@RequestMapping("properties")
public class PropertyResouce {
    // private final static Logger logger =
    // Logger.getLogger(SettleServer.class);

    @Autowired
    private HouseInfoBo houseInfoBo;

    @RequestMapping(value="/{type}", method = RequestMethod.GET)
    @ResponseBody
    public DataResult query(
            @PathVariable String type, HttpServletRequest request) {

        DataResult result = new DataResult();
//        result.setData(houseInfoBo.query(param));
//        result.setTotalCount(houseInfoBo.count(param));
        return result;
    }

    @RequestMapping(value="/{type}/{typeKey}", method = RequestMethod.GET)
    @ResponseBody
    public DataResult get(
            @PathVariable String type,
            @PathVariable String typeKey, HttpServletRequest request) {

        DataResult result = new DataResult();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public DataResult add(
            @Validated HouseAddParam param,
            BindingResult bindingResult, HttpServletRequest request) {
        DataResult result = new DataResult();
        result.setData(houseInfoBo.add(param));
        return result;
    }

}

