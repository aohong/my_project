package com.bjcre.dao;

import com.bjcre.po.UserPo;
import org.springframework.stereotype.Component;

import com.dbframe.spring.SqlTemplateProxy;

@Component
public class RegisterDao extends SqlTemplateProxy<UserPo> {

}
