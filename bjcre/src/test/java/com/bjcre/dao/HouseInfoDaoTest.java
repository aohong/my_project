package com.bjcre.dao;

import com.bjcre.po.HouseInfoPo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by aohong on 15/8/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:dao.xml"
})
public class HouseInfoDaoTest {

    @Autowired
    HouseInfoDao houseInfoDao;

    @Test
    public void test() {
        HouseInfoPo entity = new HouseInfoPo();
        entity.setAreaId(0);
        entity.setAcreage(1.0);
        entity.setAddress("2");
        entity.setConnector("3");
        entity.setConnectorMobile("4");
        entity.setDayRent(5.0);
        entity.setDescription("6");
        entity.setHouseName("7");
        entity.setHouseType(8);
        entity.setImage("9");
        entity.setInfoType(10);
        entity.setIsRegisterCompany(11);
        entity.setTitle("12");
        entity.setTotalRent(1.3);

        houseInfoDao.insert(entity);
        System.out.println(entity);

        entity.setTitle(entity.getTitle() + "+update");
        houseInfoDao.update(entity);
        System.out.println(entity);
        Assert.assertEquals("12+update", entity.getTitle());

        Assert.assertNotNull(houseInfoDao.getUniqueById(entity.getId()));
    }
}