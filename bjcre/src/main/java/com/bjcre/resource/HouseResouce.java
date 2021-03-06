
package com.bjcre.resource;

import com.bjcre.bo.HouseInfoBo;
import com.bjcre.params.HouseAddParam;
import com.bjcre.params.HouseQueryParam;
import com.bjcre.params.QueryParam;
import com.bjcre.server.web.result.BaseResult;
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
 * 房屋信息资源类
 * 
 * @author aohong
 */
@Controller
@RequestMapping("houses")
public class HouseResouce {
    // private final static Logger logger =
    // Logger.getLogger(SettleServer.class);

    @Autowired
    private HouseInfoBo houseInfoBo;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public CountDataResult query(
            @Validated HouseQueryParam param,
            BindingResult bindingResult, HttpServletRequest request) {

        CountDataResult result = new CountDataResult();
        result.setData(houseInfoBo.query(param));
        result.setTotalCount(houseInfoBo.count(param));
        return result;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataResult get(
            @PathVariable int id, HttpServletRequest request) {

        DataResult result = new DataResult();
        result.setData(houseInfoBo.get(id));
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

