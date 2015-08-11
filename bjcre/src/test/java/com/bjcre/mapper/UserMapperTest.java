package com.bjcre.mapper;

import com.bjcre.po.UserPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by aohong on 15/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:dao.xml"
})public class UserMapperTest {

    @Autowired
    private  UserMapper userMapper;

    @Test
    public void testInsert() throws Exception {
        UserPo userPo=new UserPo();
//        userPo.setId(1);
        userPo.setLoginName("aohong");
        userPo.setPassword("123456");
        userMapper.insert(userPo);
    }
}