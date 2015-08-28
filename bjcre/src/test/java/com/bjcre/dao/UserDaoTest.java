package com.bjcre.dao;

import com.bjcre.po.UserPo;
import com.dbframe.core.SqlTemplate;
import com.dbframe.core.SqlTemplateContext;
import com.dbframe.spring.SqlTemplateContextAware;
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
})
public class UserDaoTest  {
    @Autowired
    private UserDao userDao;

    @Test
    public void testGet() {
        System.out.println(userDao.getUniqueById(3));
    }

    @Test
    public void testInsert() {

        UserPo userPo=new UserPo();
//        userPo.setId(3);
        userPo.setLoginName("aohong");
        userPo.setPassword("123456");
        userPo.setType(0);
        userDao.insert(userPo);
    }
}