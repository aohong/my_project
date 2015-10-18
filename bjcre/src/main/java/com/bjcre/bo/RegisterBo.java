package com.bjcre.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjcre.dao.RegisterDao;
import com.bjcre.params.RegisterParam;
import com.bjcre.po.UserPo;
import com.bjcre.vo.UserVo;

/**
 * 
 * @author 彩色照片
 *
 */
@Service
public class RegisterBo {
	
	@Autowired
    private RegisterDao registerDao;
    
    public UserVo add(RegisterParam param) {
        UserPo po = new UserPo();
        po.setLoginName(param.getUsername());
        po.setPassword(param.getPassword());
        registerDao.insert(po);
        return this.transfer(po);
    };
    
    public UserVo transfer(UserPo po){
    	UserVo vo = new UserVo();
    	vo.setUsername(po.getLoginName());
    	vo.setPassword(po.getPassword());
    	return vo;
    }

}
